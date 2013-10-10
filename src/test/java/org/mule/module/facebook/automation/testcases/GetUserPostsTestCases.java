package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
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

public class GetUserPostsTestCases extends FacebookTestParent {
	
	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) context.getBean("getUserPostsTestData");
		
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
	public void testGetUserPosts() {
		try {
			String profileId = (String) testObjects.get("profileId");
			testObjects.put("user", profileId);
			
			final List<String> messageIds = (List<String>) testObjects.get("messageIds");
			
			MessageProcessor flow = lookupFlowConstruct("get-user-posts");
			MuleEvent response = flow.process(getTestEvent(testObjects));
						
			Collection<Post> posts = (Collection<Post>) response.getMessage().getPayload();

			// Assert that the inserted posts can be found in the user's list of posts
			Collection<Post> matching = CollectionUtils.select(posts, new Predicate() {

				@Override
				public boolean evaluate(Object object) {
					Post post = (Post) object;
					return messageIds.contains(post.getId());
				}
				
			});

			assertTrue(matching.size() == messageIds.size());
			
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
