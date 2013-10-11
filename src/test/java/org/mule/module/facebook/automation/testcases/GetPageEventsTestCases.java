/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Event;

public class GetPageEventsTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String,Object>) context.getBean("getPageEventsTestData");
		
		String pageId = (String) testObjects.get("page");
		String eventName = (String) testObjects.get("eventName");
		String startTime = (String) testObjects.get("startTime");
		
		String eventId = publishEventPage(pageId, eventName, startTime);
		testObjects.put("eventId", eventId);
	}
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetPageEvents() {
		try {
			String eventId = (String) testObjects.get("eventId");
			
			MessageProcessor flow = lookupFlowConstruct("get-page-events");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			List<Event> result = (List<Event>) response.getMessage().getPayload();
			assertTrue(result.size() == 1);
			
			Event event = result.get(0);
			assertTrue(event.getId().equals(eventId));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
    
    @After
    public void tearDown() throws Exception {
    	String eventId = (String) testObjects.get("eventId");
    	deleteObject(eventId);
    }
    
}