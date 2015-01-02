package com.search.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.search.indiegogo.Campaign;

/**
 * 
 * IndexedDatastore maintains the inverted indexes and forward indexes for the titles and tagline of the 
 * campaigns passed. 
 * 
 * @author sethuraman
 *
 */
public class IndexedDatastore 
{
	public IndexedDatastore(List<Campaign> campaigns) 
	{
		campaignsByTokensForTitle = new HashMap<String, Set<Campaign>>();
		frequencyMapOfTitleByCampaign = new HashMap<Campaign, Map<String, Integer>>();
		frequencyMapOfTaglineByCampaign = new HashMap<Campaign, Map<String, Integer>>();
		campaignsByTokensForTagline = new HashMap<String, Set<Campaign>>();
		
		for(final Campaign campaign : campaigns)
		{
			Tokenizer tokenizerForTitle = new Tokenizer(campaign.getTitle());
			
			frequencyMapOfTitleByCampaign.put(campaign, tokenizerForTitle.getFrequencyMap());
			
			for(String token : tokenizerForTitle.getAllTokens())
			{
				if(campaignsByTokensForTitle.containsKey(token))
				{
					campaignsByTokensForTitle.get(token).add(campaign);
				}
				else
				{
					campaignsByTokensForTitle.put(token, new HashSet<Campaign>(){{
						add(campaign);
					}});
				}				
			}
			
			Tokenizer tokenizerForTagline = new Tokenizer(campaign.getTagline());
			frequencyMapOfTaglineByCampaign.put(campaign, tokenizerForTagline.getFrequencyMap());
			
			for(String token : tokenizerForTagline.getAllTokens())
			{
				if(campaignsByTokensForTagline.containsKey(token))
				{
					campaignsByTokensForTagline.get(token).add(campaign);
				}
				else
				{
					campaignsByTokensForTagline.put(token, new HashSet<Campaign>(){{
						add(campaign);
					}});
				}				
			}			
		}
	}
	
	public HashMap<String, Set<Campaign>> getCampaignsByTokensForTitle() 
	{
		return campaignsByTokensForTitle;
	}
	
	public HashMap<String, Set<Campaign>> getCampaignsByTokensForTagline() 
	{
		return campaignsByTokensForTagline;
	}
	
	public HashMap<Campaign, Map<String, Integer>> getFrequencyMapOfTitleByCampaign() 
	{
		return frequencyMapOfTitleByCampaign;
	}
	public HashMap<Campaign, Map<String, Integer>> getFrequencyMapOfTaglineByCampaign() 
	{
		return frequencyMapOfTaglineByCampaign;
	}

	// Inverted indexes 
	private final HashMap<String, Set<Campaign>> campaignsByTokensForTitle;
	private final HashMap<String, Set<Campaign>> campaignsByTokensForTagline;
	
	// Forward indexes
	private final HashMap<Campaign, Map<String, Integer>> frequencyMapOfTitleByCampaign;
	private final HashMap<Campaign, Map<String, Integer>> frequencyMapOfTaglineByCampaign;
}
