package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Note;

public class GetNoteTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getNoteTestData");

		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
			
		String msg = getTestRunMessageValue("msg").toString();
		String subject = getTestRunMessageValue("subject").toString();

		String noteid = publishNote(getProfileId(), msg, subject);
		upsertOnTestRunMessage("note", noteid);
	}

	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetNote() {
		try {
			String noteId = (String) getTestRunMessageValue("note");
			String msg = (String) getTestRunMessageValue("msg");
			String subject = (String) getTestRunMessageValue("subject");
			
			Note note = runFlowAndGetPayload("get-note");
			
			assertEquals(noteId, note.getId());
			assertTrue(note.getMessage().contains(msg));
			assertEquals(note.getSubject(), subject);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}

	@After
	public void tearDown() throws Exception {
		String noteId = (String) getTestRunMessageValue("note");
		deleteObject(noteId);
	}
}
