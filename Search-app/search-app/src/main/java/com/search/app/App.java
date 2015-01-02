package com.search.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.search.engine.CoreEngine;
import com.search.engine.CoreEngine.GradedCampaign;
import com.search.engine.IndexedDatastore;
import com.search.indiegogo.Campaign;
import com.search.indiegogo.JSONImporter;

public class App 
{
	public static void openInBrowser(String url)
	{
		String os = System.getProperty("os.name").toLowerCase();
		Runtime rt = Runtime.getRuntime();
		try{
			if (os.indexOf( "win" ) >= 0) 
			{
				rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);
			} 
			else if (os.indexOf( "mac" ) >= 0) 
			{
				rt.exec( "open " + url);
			} 
			else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) 
			{
				String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
						"netscape","opera","links","lynx"};
				StringBuffer cmd = new StringBuffer();
				for (int i=0; i< browsers.length; i++)
					cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");

				rt.exec(new String[] { "sh", "-c", cmd.toString() });
			} 
			else 
			{
				return;
			}
		}
		catch(Exception e)
		{
			return;
		}
		return;	
	}

	public static void main(String[] args)
	{
		System.out.println("............Importing data from response.json.........");
		JSONImporter dataImporter = new JSONImporter();		
		System.out.println("............Indexinging campaigns by title and tagline.........");
		List<Campaign> allCampaigns = dataImporter.importCamapigns("response.json");
		IndexedDatastore dataStore = new IndexedDatastore(allCampaigns);
		System.out.println("............Initializing search engine.........");
		CoreEngine searchEngine = new CoreEngine(dataStore);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int serial = 1;
		try {
			while(true)
			{
				System.out.println("Enter your option from the list 1. Display all campaigns  2. Search by title and tagline 3. Exit\n\r");
				int option = Integer.parseInt(br.readLine());
				if(option == 3)
					break;
				switch(option)
				{
				case 1:
					serial = 1;
					for (Campaign each : allCampaigns)
					{
						System.out.println("********************************************************************************************************");
						System.out.println("Campaign - " + serial++);
						System.out.println("********************************************************************************************************");
						System.out.println(each.toString()+"\n\r");					
					}
					System.out.println("Intending to open up a campaign ?");
					String response = br.readLine();
					if(response.toLowerCase().equals("yes"))
					{
						System.out.println("Please enter a serial number from 1 - 9");
						int choice = Integer.parseInt(br.readLine());
						if(choice <=9 && choice >= 1)
						{
							openInBrowser(allCampaigns.get(choice-1).getHyperlink());
						}
						else
						{
							System.out.println("Invalid option");
						}
					}
					break;

				case 2:
					System.out.println("Enter your search query");
					String query = br.readLine();
					serial = 1;
					List<GradedCampaign> matches =  searchEngine.getMachingCampaigns(query);
					if(matches.isEmpty())
					{
						System.out.println("No campaigns matched");
						break;
					}
					for (GradedCampaign each : matches)
					{
						System.out.println("********************************************************************************************************");
						System.out.println("Campaign - " + serial++);
						System.out.println("********************************************************************************************************");
						System.out.println(each.getCampaign().toString()+ "\n");					
					}
					System.out.println("Intending to open up a campaign ?");
					response = br.readLine();
					if(response.toLowerCase().equals("yes"))
					{
						System.out.println("Please enter a serial number from 1 till " + matches.size());
						int choice = Integer.parseInt(br.readLine());
						if(choice <=matches.size() && choice >= 1)
						{
							openInBrowser(matches.get(choice-1).getCampaign().getHyperlink());
						}
						else
						{
							System.out.println("Invalid option");
						}
					}

					break;
				default:
					break;			

				}

			}
		}
		catch(IOException e)
		{
			System.out.println(e.getStackTrace());
		}


	}

}
