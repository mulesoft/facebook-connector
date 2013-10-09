/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.User;

public class GetUserTestCases extends FacebookTestParent {
	
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String,Object>) context.getBean("getUserTestData");

			User loggedInUser = getLoggedUserDetails();
			testObjects.put("username", loggedInUser.getId());
		}
		catch (Exception e) {
			e.printStackTrace(); 
			fail();
		}
	}
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUser() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-user");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			User user = (User) response.getMessage().getPayload();

			assertEquals(user.getId(), (String) testObjects.get("username"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
     
	}
    
}