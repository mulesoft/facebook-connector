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

public class GetEventAttendingTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
    	initializeTestRunMessage("getEventAttendingTestData");
		String eventId = getTestRunMessageValue("eventId");
    	attendEvent(eventId);
    	
    	String profileId = getProfileId();
    	upsertOnTestRunMessage("profileId", profileId);
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetEventAttending() {
		try {
			String profileId = getTestRunMessageValue("profileId");
			
			// Get list of users attending the event
			Collection<User> users = runFlowAndGetPayload("get-event-attending");
			// There should be at least one (us)
			assertTrue(users.size() > 0);
			
			// Loop through each user until we find ourselves
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