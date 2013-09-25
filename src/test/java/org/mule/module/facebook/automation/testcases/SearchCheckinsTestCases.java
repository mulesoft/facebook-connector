package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class SearchCheckinsTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testSearchCheckins() {
		testObjects = (HashMap<String, Object>) context
				.getBean("searchCheckinsTestData");

		MessageProcessor flow = lookupFlowConstruct("search-checkins");


		try {
	
			MuleEvent response = flow.process(getTestEvent(testObjects));
			Collection SearchResponse = (Collection<String>)  response.getMessage().getPayload();

			assertEquals(SearchResponse.isEmpty(), false);
		
		
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	

}