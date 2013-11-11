package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Comment;

public class GetNoteCommentsTestCases extends FacebookTestParent {
	// create note.
	// add comment to note

	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getNoteCommentsTestData");
		String msg = getTestRunMessageValue("msg").toString();
		String subject = getTestRunMessageValue("subject").toString();
		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
		
		String noteid = publishNote(profileId, msg, subject);
		upsertOnTestRunMessage("note", noteid);
		
		String commentId = publishComment(noteid, msg);
		upsertOnTestRunMessage("commentId", commentId);
	}

	@Category({RegressionTests.class})
	@Test
	public void testGetNoteComments() {
		try {
			String commentId = getTestRunMessageValue("commentId").toString();
			String msg = getTestRunMessageValue("msg").toString();

			List<Comment> comments = runFlowAndGetPayload("get-note-comments");

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
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	//note cannot be deleted with delete object
	@After
	public void tearDown() throws Exception {
		String note = (String) getTestRunMessageValue("note");
		deleteObject(note);
	}
}
