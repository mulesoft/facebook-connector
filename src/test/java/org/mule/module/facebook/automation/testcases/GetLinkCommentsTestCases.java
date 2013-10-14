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
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Comment;

public class GetLinkCommentsTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String, Object>) context.getBean("getLinkCommentsTestData");
		String profileId = getProfileId();
		testObjects.put("profileId", profileId);

		String msg = testObjects.get("msg").toString();
		// link is a url here
		String link = testObjects.get("link").toString();
		
		String messageId = publishLink(profileId, msg, link);
		testObjects.put("messageId", messageId);
		
		String commentId = publishComment(messageId, (String) testObjects.get("commentMsg"));
		testObjects.put("commentId", commentId);
	}

	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetLinkComments() {
		try {
			String messageId = (String) testObjects.get("messageId");
			String commentMsg = (String) testObjects.get("commentMsg");
			String commentId = (String) testObjects.get("commentId");
			
			// link is the graph object id here
			testObjects.put("link", messageId);
			
			MessageProcessor flow = lookupFlowConstruct("get-link-comments");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			List<Comment> comments = (List<Comment>) response.getMessage().getPayload();
			
			assertTrue(comments.size() == 1);
			Comment comment = comments.get(0);
			assertEquals(comment.getMessage(), commentMsg);
			assertEquals(comment.getId(), commentId);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String profileId = (String) testObjects.get("profileId");
		String messageId = (String) testObjects.get("messageId");
		deleteObject(profileId + "_" + messageId);
	}

}
