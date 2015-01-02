package com.search.indiegogo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.search.engine.CoreEngine;
import com.search.engine.CoreEngine.GradedCampaign;
import com.search.engine.IndexedDatastore;

/**
 *
 * Imports all the campaigns from a JSON file and returns them as an ArrayList of Campaigns 
 * @author sethuraman
 *
 */
public class JSONImporter 
{
	private String getFileContents(String resource)
	{
		ClassLoader classLoader = getClass().getClassLoader();
		StringBuilder contents = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(resource)));
		String line;
		try {
			line = br.readLine();
			while(line!=null)
			{
				contents.append(line);
				line = br.readLine();			
			}
			br.close();
			return contents.toString();		
		}
		catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return contents.toString();
	}	
	
	public List<Campaign> importCamapigns(String resource)
	{
		try
		{			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(getFileContents(resource));
			JSONArray response = (JSONArray) jsonObject.get("response");
			ArrayList<Campaign> campaigns = new ArrayList<Campaign>();
			Iterator i = response.iterator();
			while (i.hasNext()) 
			{
				JSONObject innerObj = (JSONObject) i.next();
				//System.out.println("id "+ innerObj.get("id") + " with title " + innerObj.get("title") + " with tagline " + innerObj.get("tagline"));
			    JSONObject images = (JSONObject) innerObj.get("image_types");
			    String photourl = (String) images.get("medium");
			    String slug = (String) innerObj.get("slug");
			    campaigns.add(new Campaign((String) innerObj.get("title"), (String) innerObj.get("tagline"), new URL(photourl), slug));
		    }
			return campaigns;
		}
		catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
		return new ArrayList<Campaign>();		
	}
}
