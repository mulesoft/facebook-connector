package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
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

public class GetVideoCommentsTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String, Object>) context.getBean("getVideoCommentsTestData");
			
		String commentId = publishComment((String) testObjects.get("postId"), (String) testObjects.get("msg"));
		testObjects.put("objectId", commentId);
	}

	@SuppressWarnings("unchecked")
	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testGetVideoComments() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-video-comments");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			List<Comment> result = (List<Comment>) response.getMessage().getPayload();

			Comment comment = result.get(0);
			assertEquals((String) testObjects.get("msg"), comment.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String objectId = (String) testObjects.get("objectId");
		deleteObject(objectId);
	}

}
