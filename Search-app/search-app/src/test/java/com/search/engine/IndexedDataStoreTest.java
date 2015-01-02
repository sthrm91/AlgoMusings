package com.search.engine;


import java.util.ArrayList;
import java.util.List;

import com.search.indiegogo.Campaign;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class IndexedDataStoreTest extends TestCase 
{
	
	public IndexedDataStoreTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( IndexedDataStoreTest.class );
    }
    
	public void testIndexedDatastoreForEmptyCampaigns()
    {
		List<Campaign> campaigns = new ArrayList<Campaign>();
		IndexedDatastore dataStore = new IndexedDatastore(campaigns);
		assertTrue(dataStore.getCampaignsByTokensForTagline().isEmpty());
		assertTrue(dataStore.getCampaignsByTokensForTitle().isEmpty());
		assertTrue(dataStore.getFrequencyMapOfTaglineByCampaign().isEmpty());
		assertTrue(dataStore.getCampaignsByTokensForTitle().isEmpty());
    }
	
	public void testIndexedDatastoreForFewCampaigns()
    {
		List<Campaign> campaigns = new ArrayList<Campaign>();
		campaigns.add(new Campaign("title1", "tagline1", null, "hyperlink1"));
		campaigns.add(new Campaign("title2", "tagline2", null, "hyperlink2"));
		campaigns.add(new Campaign("title3", "tagline3", null, "hyperlink3"));
		IndexedDatastore dataStore = new IndexedDatastore(campaigns);
		
		// verify that index by title doesn't mix up with index by tagline
		assertTrue(dataStore.getCampaignsByTokensForTagline().get("title1") == null);
		assertTrue(dataStore.getCampaignsByTokensForTagline().get("title2") == null);
		assertTrue(dataStore.getCampaignsByTokensForTagline().get("title3") == null);
		assertTrue(dataStore.getCampaignsByTokensForTitle().get("title1").contains(campaigns.get(0)));
		assertTrue(dataStore.getCampaignsByTokensForTitle().get("title1").size() == 1);
		assertTrue(dataStore.getCampaignsByTokensForTitle().get("tagline1") == null);
		assertTrue(dataStore.getCampaignsByTokensForTitle().get("tagline2") == null);
		assertTrue(dataStore.getCampaignsByTokensForTitle().get("tagline3") == null);
		// verify the forward indexes
		assertTrue(dataStore.getFrequencyMapOfTaglineByCampaign().get(campaigns.get(0)).get("tagline1") == 1);
		assertTrue(dataStore.getFrequencyMapOfTitleByCampaign().get(campaigns.get(0)).get("title1") == 1);
    }

}
