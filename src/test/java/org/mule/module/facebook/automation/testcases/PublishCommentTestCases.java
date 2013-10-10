package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Comment;

public class PublishCommentTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			//create comment for message
			testObjects = (HashMap<String, Object>) context.getBean("publishCommentTestData");
			
			String statusMsg = (String) testObjects.get("msg");
			String postId = publishMessage(getProfileId(), statusMsg);
			testObjects.put("postId", postId);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testPublishComment() {
		try {
			String postId = (String) testObjects.get("postId");
			String msg = (String) testObjects.get("msg");
			
			MessageProcessor flow = lookupFlowConstruct("publish-comment");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			String commentId = (String) response.getMessage().getPayload();

			List<Comment> comments = getStatusComments(postId);
			boolean commentFound = false;
			for (Comment comment : comments) {
				if (comment.getId().equals(commentId)) {
					commentFound = true;
					assertEquals(msg, comment.getMessage());
					break;
				}
			}

			assertTrue("Comment not found", commentFound);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}
	
	@After
	public void tearDown(){
		try {
			deleteObject(testObjects.get("postId").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
