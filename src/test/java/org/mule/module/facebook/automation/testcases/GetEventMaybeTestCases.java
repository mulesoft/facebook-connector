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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.User;

public class GetEventMaybeTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
    	initializeTestRunMessage("getEventMaybeTestData");
    	
    	String profileId = getProfileId();
    	upsertOnTestRunMessage("profileId", profileId);
    	
    	String eventId = getTestRunMessageValue("eventId");
    	tentativeEvent(eventId);
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetEventMaybe() {
		try {
			String profileId = getTestRunMessageValue("profileId");
			
			Collection<User> users = runFlowAndGetPayload("get-event-maybe");
			assertTrue(users.size() > 0);
			
			boolean found = false;
			for (User user : users) {
				if (user.getId().equals(profileId)) {
					found = true;
					break;
				}
			}
			assertTrue(found);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
    
	@After
	public void tearDown() throws Exception {
		String eventId = getTestRunMessageValue("eventId");
		declineEvent(eventId);
	}
	
}