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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class GetEventPictureTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
	    	testObjects = (HashMap<String,Object>) context.getBean("getEventPictureTestData");
			
			String profileId = getProfileId();
			
			String eventId = publishEvent(profileId, (String) testObjects.get("eventName"), 
					(String) testObjects.get("startTime"));
			
			testObjects.put("eventId", eventId);
			testObjects.put("objectId", eventId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testEventPicture() {
    	
		try {
			MessageProcessor flow = lookupFlowConstruct("get-event-picture");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			byte[] picture = (byte[]) response.getMessage().getPayload();
			
			assertTrue(picture.length > 0);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
     
	}
    
    @After
	public void tearDown() {
		try {
			deleteObject((String) testObjects.get("objectId"));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
    
}