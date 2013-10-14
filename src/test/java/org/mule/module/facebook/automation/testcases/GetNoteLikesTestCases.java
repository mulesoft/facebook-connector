package org.mule.module.facebook.automation.testcases;

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

import com.restfb.types.NamedFacebookType;
import com.restfb.types.Post.Likes;

public class GetNoteLikesTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String, Object>) getBeanFromContext("getNoteLikesTestData");

		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
		
		String msg = testObjects.get("msg").toString();
		String subject = testObjects.get("subject").toString();

		String noteid = publishNote(getProfileId(), msg, subject);
		testObjects.put("note", noteid);
		
		like(noteid);
	}

	@Test
	public void testGetNoteLikes() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-note-likes");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			Likes likes = (Likes) response.getMessage().getPayload();
			
			List<NamedFacebookType> data = likes.getData();
			assertTrue(data.size() == 1);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}

	@After
	public void tearDown() throws Exception {
		String noteId = (String) testObjects.get("note");
		deleteObject(noteId);
	}
}
