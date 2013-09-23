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

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.User;

public class GetUserTestCases extends FacebookTestParent {
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUser() {
    	
    	testObjects = (HashMap<String,Object>) context.getBean("getUserTestData");
    	
		MessageProcessor flow = lookupFlowConstruct("get-user");
    	
		try {

			MuleEvent response = flow.process(getTestEvent(testObjects));
			User user = (User) response.getMessage().getPayload();
			
			assertTrue(user.getUsername().equals(testObjects.get("username").toString()));

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
     
	}
    
}