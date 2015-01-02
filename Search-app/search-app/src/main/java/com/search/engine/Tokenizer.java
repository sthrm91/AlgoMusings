package com.search.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * Tokenizer is used to spilt a query string into a set of tokens
 * Provides methods to obtain all the tokens in the query string as well as frequency map of these tokens 
 * @author sethuraman 
 *
 */
public class Tokenizer 
{
	public Tokenizer(String query)
	{
		stringTokenizer = new StringTokenizer(query, delimiter);
		tokens = new ArrayList<String>();
	}
	
	public List<String> getAllTokens()
	{
		if(tokens.isEmpty())
		{
			while(stringTokenizer.hasMoreTokens())
			{
				tokens.add(stringTokenizer.nextToken().toLowerCase());
			}
		}		
		return tokens;
	}	

	/*
	 * Frequency map is one of the vector representations of text 
	 */

	public Map<String, Integer> getFrequencyMap()
	{
		HashMap<String, Integer> frequencyMap = new HashMap<String, Integer>();		
		for(String word : getAllTokens())
		{
			if(frequencyMap.containsKey(word))
			{
				frequencyMap.put(word, frequencyMap.get(word) + 1);
			}
			else
			{
				frequencyMap.put(word, 1);
			}
		}		
		return frequencyMap;		
	}
	
	private ArrayList<String> tokens;
	private StringTokenizer stringTokenizer;
	private static final String delimiter =  ",.-:'! \t\n\r\f";	
}
