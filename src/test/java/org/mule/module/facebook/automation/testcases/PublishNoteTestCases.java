package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Note;

public class PublishNoteTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("publishNoteTestData");
			String profileId = getProfileId();
			testObjects.put("profileId", profileId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testPublishNote() {
		try {
			MessageProcessor flow = lookupFlowConstruct("publish-note");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			String id = (String) response.getMessage().getPayload();
			assertTrue(StringUtils.isNotEmpty(id));
			testObjects.put("noteId", id);

			Note note = getNote(id);
			assertTrue(note.getId().equals(id));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String id = (String) testObjects.get("noteId");
			deleteObject(id);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
