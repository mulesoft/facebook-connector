package org.mule.module.facebook.automation.testcases;

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

import com.restfb.types.Post;

public class GetEventWallTestCases extends FacebookTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getEventWallTestData");
			
			String profileId = getProfileId();
			
			String eventName = (String) testObjects.get("eventName");
			String startTime = (String) testObjects.get("startTime");
			String eventId = publishEvent(profileId, eventName, startTime);
			testObjects.put("eventId", eventId);
			
			List<String> messages = (List<String>) testObjects.get("messages");
			List<String> messageIds = new ArrayList<String>();
			for (String message : messages) {
				String messageId = publishMessage(eventId, message);
				messageIds.add(messageId);
			}
			testObjects.put("messageIds", messageIds);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetEventWall() {
		try {
			String profileId = (String) testObjects.get("profileId");
			List<String> messageIds = (List<String>) testObjects.get("messageIds");
			
			MessageProcessor flow = lookupFlowConstruct("get-event-wall");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<Post> wall = (List<Post>) response.getMessage().getPayload();
			for (Post post : wall) {
				assertTrue(messageIds.contains(post.getId()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			List<String> messageIds = (List<String>) testObjects.get("messageIds");
			for (String messageId : messageIds) {
				deleteObject(messageId);
			}
			String eventId = (String) testObjects.get("eventId");
			deleteObject(eventId);
		}
		catch (Exception e) { 
			e.printStackTrace();
			fail();
		}
	}
	
}
