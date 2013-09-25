package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class DislikeTestCases extends FacebookTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("dislikeTestData");
			
			String profileId = getProfileId();
			testObjects.put("profileId", profileId);
			
			String msg = (String) testObjects.get("msg");
			String messageId = publishMessage(profileId, msg);
			testObjects.put("messageId", messageId);
			
			like(messageId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testDislike() {
		try {
			String messageId = (String) testObjects.get("messageId");
			testObjects.put("postId", messageId);
			
			MessageProcessor flow = lookupFlowConstruct("dislike");
			MuleEvent response = flow.process(getTestEvent(testObjects));
						
			Boolean result = (Boolean) response.getMessage().getPayload();
			assertTrue(result);
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
