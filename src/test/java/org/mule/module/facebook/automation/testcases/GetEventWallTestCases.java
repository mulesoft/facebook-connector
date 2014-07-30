/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
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

import com.restfb.types.Post;

public class GetEventWallTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getEventWallTestData");
		
		String profileId = getProfileId();
		
		String eventName = (String) getTestRunMessageValue("eventName");
		String startTime = (String) getTestRunMessageValue("startTime");
		String eventId = publishEvent(profileId, eventName, startTime);
		upsertOnTestRunMessage("eventId", eventId);
		
		List<String> messages = (List<String>) getTestRunMessageValue("messages");
		List<String> messageIds = new ArrayList<String>();
		for (String message : messages) {
			String messageId = publishMessage(eventId, message);
			messageIds.add(messageId);
		}
		upsertOnTestRunMessage("messageIds", messageIds);
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetEventWall() {
		try {
			List<String> messageIds = (List<String>) getTestRunMessageValue("messageIds");
			
			List<Post> wall = runFlowAndGetPayload("get-event-wall");
			assertEquals(wall.size(), messageIds.size());
			for (Post post : wall) {
				assertTrue(messageIds.contains(post.getId()));
			}
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception{
		String eventId = (String) getTestRunMessageValue("eventId");
		deleteObject(eventId);
	}
	
}
