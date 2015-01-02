package com.search.engine;

import java.util.ArrayList;
import java.util.List;

import com.search.indiegogo.Campaign;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CoreEngineTest extends TestCase 
{
	public CoreEngineTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite(CoreEngineTest.class );
    }
    
    public void testCoreEngineBasic()
    {
    	List<Campaign> campaigns = new ArrayList<Campaign>();
		campaigns.add(new Campaign("title1", "tagline1", null, "hyperlink1"));
		campaigns.add(new Campaign("title2", "tagline2", null, "hyperlink2"));
		campaigns.add(new Campaign("title3 title4", "tagline3", null, "hyperlink3"));
		IndexedDatastore dataStore = new IndexedDatastore(campaigns);
    	CoreEngine engine = new CoreEngine(dataStore);
    	assertTrue(engine.getMachingCampaigns("title1").size() == 1);
    	assertTrue(engine.getMachingCampaigns("tagline1").size() == 1);
    	assertTrue(engine.getMachingCampaigns("tagline1").get(0).getCampaign().getTagline().equals("tagline1"));
    	assertTrue(engine.getMachingCampaigns("title1 title3").size() == 2);
    	assertTrue(engine.getMachingCampaigns("title4 title3").size() == 1);    	
    }

}
