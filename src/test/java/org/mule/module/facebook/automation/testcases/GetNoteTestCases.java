package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Note;

public class GetNoteTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String, Object>) context.getBean("getNoteTestData");

		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
			
		String msg = testObjects.get("msg").toString();
		String subject = testObjects.get("subject").toString();

		String noteid = publishNote(getProfileId(), msg, subject);
		testObjects.put("note", noteid);
	}

	@Test
	public void testGetNote() {
		try {
			String noteId = (String) testObjects.get("note");
			String msg = (String) testObjects.get("msg");
			String subject = (String) testObjects.get("subject");
			
			MessageProcessor flow = lookupFlowConstruct("get-note");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			Note note = (Note) response.getMessage().getPayload();
			
			assertEquals(noteId, note.getId());
			assertTrue(note.getMessage().contains(msg));
			assertEquals(note.getSubject(), subject);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void tearDown() throws Exception {
		String noteId = (String) testObjects.get("note");
		deleteObject(noteId);
	}
}
