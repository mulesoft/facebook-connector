/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.StatusMessage;

public class GetUserStatusesTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getUserStatusesTestData");
		
		String profileId = getProfileId();
		
		List<String> messages = (List<String>) getTestRunMessageValue("messages");
		List<String> messageIds = new ArrayList<String>();
		for (String message : messages) {
			String messageId = publishMessage(profileId, message);
			messageIds.add(messageId);
		}
		upsertOnTestRunMessage("messageIds", messageIds);
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetUserStatuses() {
		try {
			String profileId = (String) getTestRunMessageValue("profileId");
			upsertOnTestRunMessage("user", profileId);
			
			List<String> messageIds = (List<String>) getTestRunMessageValue("messageIds");
			
			List<StatusMessage> statuses = runFlowAndGetPayload("get-user-statuses");
			
			assertEquals(statuses.size(), messageIds.size());
			for (StatusMessage status : statuses) {
				// MessageId is of the form {userId}_{messageId}
				// StatusId is of the form {messageId}
				// So we assert by concatenating profileId (our userId) with statusId
				assertTrue(messageIds.contains(profileId + "_" + status.getId()));
			}
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		List<String> messageIds = (List<String>) getTestRunMessageValue("messageIds");
		for (String messageId : messageIds) {
			deleteObject(messageId);
		}
	}
	
}
