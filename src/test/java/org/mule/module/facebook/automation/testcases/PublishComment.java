package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Comment;

public class PublishComment extends FacebookTestParent {
	@SuppressWarnings("unchecked")
	@BeforeClass
	public void setUp() {
		
		//create comment for message
		testObjects = (HashMap<String, Object>) context
				.getBean("publishCommentTestData");

		try {
			testObjects.put("postId", publishMessage(testObjects.get("profileId")
					.toString(), testObjects.get("statusmsg").toString()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

	}

	@Category({ RegressionTests.class })
	@Test
	public void PublishCommentTestCase() {

		MessageProcessor flow = lookupFlowConstruct("publish-comment");

		try {

			MuleEvent response = flow.process(getTestEvent(testObjects));

			String commentID = (String) response.getMessage().getPayload();

			testObjects.put("commentID",
					FacebookConnectorTestUtils.getId(commentID));

			List<Comment> comments = getStatusComments(testObjects
					.get("postId").toString());

			boolean commentFound = false;

			for (Comment comment : comments) {

				if (comment.getId().equals(
						testObjects.get("commentID").toString())) {
					commentFound = true;
					assertEquals(testObjects.get("msg").toString(),
							comment.getMessage());
				}
			}

			if (commentFound == false) {
				fail("Comment Not found");

			}

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}
	
	
	
	
	@AfterClass
	public void tearDown(){
		
		
		//Delete comment, Delete Message
		try {
			deleteObject(testObjects.get("commentID").toString());
			deleteObject(testObjects.get("postId").toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
