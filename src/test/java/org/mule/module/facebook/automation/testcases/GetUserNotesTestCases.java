package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Note;

public class GetUserNotesTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String, Object>) context.getBean("publishNoteTestData");
		String msg = testObjects.get("msg").toString();
		String subject = testObjects.get("subject").toString();

		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
		String noteid = publishNote(profileId, msg, subject);
		testObjects.put("noteid", noteid);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetUserNotes() {
		try {
			String noteId = testObjects.get("noteid").toString();

			MessageProcessor flow = lookupFlowConstruct("get-user-notes");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			List<Note> notes = (List<Note>) response.getMessage().getPayload();

			Boolean found = false;

			for (Note note : notes) {
				if (note.getId().equals(noteId)) {
					found = true;
					assertTrue(note.getMessage().contains(testObjects.get("msg").toString()));
					assertEquals(note.getSubject(), testObjects.get("subject")
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
		String noteId = testObjects.get("noteid").toString();
		deleteObject(noteId);
	}
}
