package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.StatusMessage;

public class GetUserStatusesTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) context.getBean("getUserStatusesTestData");
		
		String profileId = getProfileId();
		
		List<String> messages = (List<String>) testObjects.get("messages");
		List<String> messageIds = new ArrayList<String>();
		for (String message : messages) {
			String messageId = publishMessage(profileId, message);
			messageIds.add(messageId);
		}
		testObjects.put("messageIds", messageIds);
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetUserStatuses() {
		try {
			String profileId = (String) testObjects.get("profileId");
			testObjects.put("user", profileId);
			
			List<String> messageIds = (List<String>) testObjects.get("messageIds");
			
			MessageProcessor flow = lookupFlowConstruct("get-user-statuses");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<StatusMessage> statuses = (List<StatusMessage>) response.getMessage().getPayload();
			
			assertEquals(statuses.size(), messageIds.size());
			for (StatusMessage status : statuses) {
				// MessageId is of the form {userId}_{messageId}
				// StatusId is of the form {messageId}
				// So we assert by concatenating profileId (our userId) with statusId
				assertTrue(messageIds.contains(profileId + "_" + status.getId()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() throws Exception {
		List<String> messageIds = (List<String>) testObjects.get("messageIds");
		for (String messageId : messageIds) {
			deleteObject(messageId);
		}
	}
	
}
