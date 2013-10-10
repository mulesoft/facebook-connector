package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Post;

public class GetUserTaggedTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getUserTaggedTestData");
			
			String profileId = getProfileId();
			testObjects.put("profileId", profileId);

			String msg = (String) testObjects.get("msg");
			String messageId = publishMessage(profileId, msg);
			testObjects.put("messageId", messageId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserTagged() {
		try {
			String profileId = (String) testObjects.get("profileId");
			
			MessageProcessor flow = lookupFlowConstruct("get-user-tagged");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			List<Post> posts = (List<Post>) response.getMessage().getPayload();
			
			boolean found = false;
			for (Post post : posts) {
				if (post.getFrom().getId().equals(profileId)) {
					found = true;
					break;
				}
			}
			assertTrue(found);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String messageId = (String) testObjects.get("messageId");
			deleteObject(messageId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
