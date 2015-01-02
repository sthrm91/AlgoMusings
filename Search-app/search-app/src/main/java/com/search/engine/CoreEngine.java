package com.search.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.search.indiegogo.Campaign;

/**
 * 
 * Core part of the search engine. Finds the relevant campaigns for a query using cosine similarity with title and tagline
 * vectors of all the campaigns 
 * 
 * @author sethuraman
 *
 */

public class CoreEngine 
{
	/**
	 *
	 * Campaign with a score calculated from cosine similarity with a query string
	 * @author sethuraman
	 *
	 */
	public static class GradedCampaign implements Comparable<GradedCampaign>
	{
		public GradedCampaign(Campaign campaign, double score)
		{
			this.campaign = campaign;
			this.score = score;
		}
		
		public Campaign getCampaign() 
		{
			return campaign;
		}

		public double getScore() 
		{
			return score;
		}
		
		public void setScore(double newScore) 
		{
			score = newScore;
		}
		
		private final Campaign campaign;

		private double score;

		@Override
		public int compareTo(GradedCampaign o) 
		{
			return new Double(o.getScore()).compareTo(this.getScore());
		}			
	}
	
	public CoreEngine(IndexedDatastore dataStore)
	{
		this.dataStore = dataStore;
	}
	
	
	private double getDotProduct(Map<String, Integer> vectorA, Map<String, Integer> vectorB)
	{
		double dotProduct = 0.0;
		
		for(String dimension : vectorA.keySet())
		{
			if(vectorB.containsKey(dimension))
			{
				dotProduct += vectorA.get(dimension) * vectorB.get(dimension);
			}
		}
		
		return dotProduct;
	}
	
	private double getCosineSimilarity(Map<String, Integer> vectorA, Map<String, Integer> vectorB)
	{
		double modA = Math.sqrt(getDotProduct(vectorA, vectorA));
		double modB = Math.sqrt(getDotProduct(vectorB, vectorB));
		double aDotB = getDotProduct(vectorA, vectorB);
		return aDotB/(modA * modB);		
	}
	
	/*
	 * Given a query string, inverted index and a forward index, returns the most relevant campaigns
	 * indexes may be on the title or tagline
	 */
	private List<GradedCampaign> getMatchingCampaigns(String query, HashMap<String, Set<Campaign>> campaignsByTokens, 
			HashMap<Campaign, Map<String, Integer>> frequencyMapByCampaign)
	{
		HashSet<Campaign> matchedCampaigns = new HashSet<Campaign>();
		List<GradedCampaign> gradedCamapaigns = new ArrayList<GradedCampaign>();
		Tokenizer queryTokenizer = new Tokenizer(query);
		for (String token : queryTokenizer.getAllTokens())
		{
			if(campaignsByTokens.containsKey(token))
			{
				matchedCampaigns.addAll(campaignsByTokens.get(token));
			}
		}
		
		for(Campaign campaign : matchedCampaigns)
		{
			Map<String, Integer> vectorA = new HashMap<String, Integer>(frequencyMapByCampaign.get(campaign));
			Map<String, Integer> vectorB = queryTokenizer.getFrequencyMap();
			gradedCamapaigns.add(new GradedCampaign(campaign, getCosineSimilarity(vectorA, vectorB)));
	    }
		
		Collections.sort(gradedCamapaigns);
		return gradedCamapaigns;
	}
	
	public List<GradedCampaign> getMatchingCampaignsByTitle(String query)
	{
		return getMatchingCampaigns(query, dataStore.getCampaignsByTokensForTitle(), dataStore.getFrequencyMapOfTitleByCampaign());		
	}
	
	public List<GradedCampaign> getMatchingCampaignsByTagline(String query)
	{
		return getMatchingCampaigns(query, dataStore.getCampaignsByTokensForTagline(), dataStore.getFrequencyMapOfTaglineByCampaign());
	}
	
	public List<GradedCampaign> getMachingCampaigns(String query)
	{
		ArrayList<GradedCampaign> campaigns = new ArrayList<GradedCampaign>();
		List<GradedCampaign> matchedByTitle = getMatchingCampaignsByTitle(query);
		List<GradedCampaign> matchedByTagline = getMatchingCampaignsByTagline(query);
		HashMap<Campaign, GradedCampaign> match = new HashMap<Campaign, GradedCampaign>();
		for(GradedCampaign each : matchedByTitle)
		{
			match.put(each.getCampaign(), each);			
		}
		
		for(GradedCampaign each : matchedByTagline)
		{
			if(match.containsKey(each.getCampaign()))
			{
				match.get(each.getCampaign()).score += each.getScore(); 
			}
			else
			{
				match.put(each.getCampaign(), each);
			}
		}
		
		campaigns.addAll(match.values());
		Collections.sort(campaigns);
		return campaigns;
	}
	
	private final IndexedDatastore dataStore;

}
