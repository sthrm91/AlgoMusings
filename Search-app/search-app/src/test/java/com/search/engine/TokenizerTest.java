package com.search.engine;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TokenizerTest extends TestCase 
{
	public TokenizerTest( String testName )
    {
        super(testName);
    }

    public static Test suite()
    {
        return new TestSuite(TokenizerTest.class);
    }
    
    public void testTokenizerBasic()
    {
    	Tokenizer tokenizer = new Tokenizer("query-1 query2 query query run!., .-");
    	assertTrue(tokenizer.getFrequencyMap().get("query") == 3);
    	assertTrue(tokenizer.getFrequencyMap().get("query2") == 1);
    	assertTrue(tokenizer.getFrequencyMap().get("query3") == null);
    	
    	// verify the absence of delimiters
    	assertTrue(tokenizer.getFrequencyMap().get(".") == null);
    	assertTrue(tokenizer.getFrequencyMap().get("-") == null);
    	assertTrue(tokenizer.getFrequencyMap().get("1") == 1);
    }
    

}
