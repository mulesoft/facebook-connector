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

import java.util.Collection;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.User;

public class GetEventNoReplyTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
	    	testObjects = (HashMap<String,Object>) context.getBean("getEventNoReplyTestData");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetEventNoReply() {
		
		MessageProcessor flow = lookupFlowConstruct("get-event-no-reply");
    	
		try {

			MuleEvent response = flow.process(getTestEvent(testObjects));
			Collection<User> users =  (Collection<User>) response.getMessage().getPayload();
			
			assertTrue(users instanceof Collection);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
     
	}
    
}