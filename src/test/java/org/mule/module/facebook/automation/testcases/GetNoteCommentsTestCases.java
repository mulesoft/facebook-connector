package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Comment;

public class GetNoteCommentsTestCases extends FacebookTestParent {
	// create note.
	// add comment to note

	@Before
	public void tearUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("getNoteCommentsTestData");
			String msg = testObjects.get("msg").toString();
			String subject = testObjects.get("subject").toString();
			String profileId = getProfileId();
			testObjects.put("profileId", profileId);
			
			String noteid = publishNote(profileId, msg, subject);
			testObjects.put("note", noteid);
			
			String commentId = publishComment(noteid, msg);
			testObjects.put("commentId", commentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Category({RegressionTests.class})
	@Test
	public void testGetNoteComments() {
		try {
			String commentId = testObjects.get("commentId").toString();
			String msg = testObjects.get("msg").toString();

			MessageProcessor flow = lookupFlowConstruct("get-note-comments");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			List<Comment> comments = (List<Comment>) response.getMessage().getPayload();

			Boolean found = false;

			for (Comment comment : comments) {
				if (comment.getId().toString().equals(commentId)) {
					assertEquals(msg, comment.getMessage());
					found = true;
					break;
				}
			}
			assertTrue(found);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//note cannot be deleted with delete object
	@After
	public void tearDown(){
		try {
			String note = (String) testObjects.get("note");
			deleteObject(note);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
