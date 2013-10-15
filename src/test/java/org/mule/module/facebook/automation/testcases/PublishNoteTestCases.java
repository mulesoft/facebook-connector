package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Note;

public class PublishNoteTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("publishNoteTestData");

		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testPublishNote() {
		try {
			String id = runFlowAndGetPayload("publish-note");

			assertTrue(StringUtils.isNotEmpty(id));
			upsertOnTestRunMessage("noteId", id);

			Note note = getNote(id);
			assertTrue(note.getId().equals(id));
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String id = (String) getTestRunMessageValue("noteId");
		deleteObject(id);
	}
}
