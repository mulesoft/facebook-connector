package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Note;

public class GetUserNotesTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("publishNoteTestData");
		String msg = getTestRunMessageValue("msg").toString();
		String subject = getTestRunMessageValue("subject").toString();

		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
		String noteid = publishNote(profileId, msg, subject);
		upsertOnTestRunMessage("noteid", noteid);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetUserNotes() {
		try {
			String noteId = getTestRunMessageValue("noteid").toString();

			List<Note> notes = runFlowAndGetPayload("get-user-notes");

			Boolean found = false;

			for (Note note : notes) {
				if (note.getId().equals(noteId)) {
					found = true;
					assertTrue(note.getMessage().contains(getTestRunMessageValue("msg").toString()));
					assertEquals(note.getSubject(), getTestRunMessageValue("subject")
							.toString());
				}

			}
			assertTrue(found);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}

	}

	@After
	public void tearDown() throws Exception {
		String noteId = getTestRunMessageValue("noteid").toString();
		deleteObject(noteId);
	}
}
