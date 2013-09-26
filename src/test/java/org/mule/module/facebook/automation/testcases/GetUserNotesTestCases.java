package org.mule.module.facebook.automation.testcases;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Note;

public class GetUserNotesTestCases extends FacebookTestParent {

	@Before
	public void tearUp() {
		try {
			testObjects = (HashMap<String, Object>) context
					.getBean("publishNoteTestData");
			String msg = testObjects.get("msg").toString();
			String subject = testObjects.get("subject").toString();

			String profileId = getProfileId();
			testObjects.put("profileId", profileId);
			String noteid = publishNote(profileId, msg, subject);
			testObjects.put("noteid", noteid);
		} catch (Exception e) {

			e.printStackTrace();
			fail();
		}
	}

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
			fail();
			e.printStackTrace();
		}

	}

	@After
	public void tearDown() {
		String noteId = testObjects.get("noteid").toString();
		try {
			deleteObject(noteId);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
