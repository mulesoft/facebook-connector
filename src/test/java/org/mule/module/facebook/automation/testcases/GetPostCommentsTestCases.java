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

import com.restfb.types.Comment;

public class GetPostCommentsTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) context.getBean("getPostCommentsTestData");
		
		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
		
		String msg = (String) testObjects.get("msg");
		String messageId = publishMessage(profileId, msg);
		testObjects.put("messageId", messageId);
		
		List<String> comments = (List<String>) testObjects.get("comments");
		List<String> commentIds = new ArrayList<String>();
		for (String comment : comments) {
			String commentId = publishComment(messageId, comment);
			commentIds.add(commentId);
		}
		
		testObjects.put("commentIds", commentIds);
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetPostComments() {
		try {
			String messageId = (String) testObjects.get("messageId");
			testObjects.put("post", messageId);
			List<String> commentIds = (List<String>) testObjects.get("commentIds");
			
			MessageProcessor flow = lookupFlowConstruct("get-post-comments");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<Comment> postComments = (List<Comment>) response.getMessage().getPayload();

			assertEquals(postComments.size(), commentIds.size());
			for (Comment comment : postComments) {
				assertTrue(commentIds.contains(comment.getId()));
			}
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
