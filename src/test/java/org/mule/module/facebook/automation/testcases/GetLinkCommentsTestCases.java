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

public class GetLinkCommentsTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp(){
		try {
			testObjects = (HashMap<String, Object>) context.getBean("getLinkCommentsTestData");
			String profileId = getProfileId();
			testObjects.put("profileId", profileId);

			String msg = testObjects.get("msg").toString();
			// link is a url here
			String link = testObjects.get("link").toString();
			
			String messageId = publishLink(profileId, msg, link);
			testObjects.put("messageId", messageId);
			
			publishComment(messageId, (String) testObjects.get("commentMsg"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetLinkComments(){
		try {
			String messageId = (String) testObjects.get("messageId");
			// link is the graph object id here
			testObjects.put("link", messageId);
			
			MessageProcessor flow = lookupFlowConstruct("get-link-comments");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			List<Comment> comments = (List<Comment>) response.getMessage().getPayload();
			
			assertTrue(comments.size() == 1);
			assertEquals((String) testObjects.get("commentMsg"), comments.get(0).getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String profileId = (String) testObjects.get("profileId");
			String messageId = (String) testObjects.get("messageId");
			deleteObject(profileId + "_" + messageId);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
