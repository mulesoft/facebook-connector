package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Post;

public class GetUserWallTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) context.getBean("getUserWallTestData");
		
		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
		
		List<String> messages = (List<String>) testObjects.get("messages");
		List<String> messageIds = new ArrayList<String>();
		
		for (String message : messages) {
			String messageId = publishMessage(profileId, message);
			messageIds.add(messageId);
			testObjects.put("messageIds", messageIds);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserWall() {
		try {
			String profileId = (String) testObjects.get("profileId");
			testObjects.put("user", profileId);
			
			final List<String> messageIds = (List<String>) testObjects.get("messageIds");
			
			MessageProcessor flow = lookupFlowConstruct("get-user-wall");
			MuleEvent response = flow.process(getTestEvent(testObjects));
						
			Collection<Post> posts = (Collection<Post>) response.getMessage().getPayload();
			
			Collection<Post> matching = CollectionUtils.select(posts, new Predicate() {
				
				@Override
				public boolean evaluate(Object object) {
					Post post = (Post) object;
					return messageIds.contains(post.getId());
				}
			});
			
			assertEquals(matching.size(), messageIds.size());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@After
	public void tearDown() throws Exception {
		List<String> messageIds = (List<String>) testObjects.get("messageIds");
		for (String messageId : messageIds) {
			deleteObject(messageId);
		}
	}
}
