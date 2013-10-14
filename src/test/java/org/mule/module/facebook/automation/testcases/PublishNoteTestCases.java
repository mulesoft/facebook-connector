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
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Note;

public class PublishNoteTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String, Object>) getBeanFromContext("publishNoteTestData");

		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
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
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String id = (String) testObjects.get("noteId");
		deleteObject(id);
	}
}
