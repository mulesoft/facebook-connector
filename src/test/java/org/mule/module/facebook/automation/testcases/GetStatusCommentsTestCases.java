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
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Comment;

public class GetStatusCommentsTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) context.getBean("getStatusCommentsTestData");
		
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
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetStatusComments() {
		try {
			String messageId = (String) testObjects.get("messageId");
			testObjects.put("status", messageId);
			List<String> commentIds = (List<String>) testObjects.get("commentIds");
			
			MessageProcessor flow = lookupFlowConstruct("get-status-comments");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<Comment> statusComments = (List<Comment>) response.getMessage().getPayload();

			assertEquals(statusComments.size(), commentIds.size());
			for (Comment comment : statusComments) {
				assertTrue(commentIds.contains(comment.getId()));
			}
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String messageId = (String) testObjects.get("messageId");
		deleteObject(messageId);
	}

}
