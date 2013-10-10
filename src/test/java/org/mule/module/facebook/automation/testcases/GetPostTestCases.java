package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Post;

public class GetPostTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) context.getBean("getPostTestData");
		
		String profileId = getProfileId();
		String msg = (String) testObjects.get("msg");
		
		String messageId = publishMessage(profileId, msg);
		testObjects.put("messageId", messageId);
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetPost() {
		try {
			String message = (String) testObjects.get("msg");
			String messageId = (String) testObjects.get("messageId");
			testObjects.put("post", messageId);
			
			MessageProcessor flow = lookupFlowConstruct("get-post");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Post post = (Post) response.getMessage().getPayload();
			assertEquals(post.getId(), messageId);
			assertEquals(post.getMessage(), message);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String messageId = (String) testObjects.get("messageId");
		deleteObject(messageId);
	}
	
}
