package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.NamedFacebookType;
import com.restfb.types.Post.Likes;

public class GetNoteLikesTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getNoteLikesTestData");

		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
		
		String msg = getTestRunMessageValue("msg").toString();
		String subject = getTestRunMessageValue("subject").toString();

		String noteid = publishNote(getProfileId(), msg, subject);
		upsertOnTestRunMessage("note", noteid);
		
		like(noteid);
	}

	@Category({RegressionTests.class})
	@Test
	public void testGetNoteLikes() {
		try {
			Likes likes = runFlowAndGetPayload("get-note-likes");
			
			List<NamedFacebookType> data = likes.getData();
			assertTrue(data.size() == 1);
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
