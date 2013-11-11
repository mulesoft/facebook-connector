/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Comment;

public class GetStatusCommentsTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getStatusCommentsTestData");
		
		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
		
		String msg = (String) getTestRunMessageValue("msg");
		String messageId = publishMessage(profileId, msg);
		upsertOnTestRunMessage("messageId", messageId);
		
		List<String> comments = (List<String>) getTestRunMessageValue("comments");
		List<String> commentIds = new ArrayList<String>();
		for (String comment : comments) {
			String commentId = publishComment(messageId, comment);
			commentIds.add(commentId);
		}
		
		upsertOnTestRunMessage("commentIds", commentIds);
	}
	
	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetStatusComments() {
		try {
			String messageId = (String) getTestRunMessageValue("messageId");
			upsertOnTestRunMessage("status", messageId);
			List<String> commentIds = (List<String>) getTestRunMessageValue("commentIds");
			
			List<Comment> statusComments = runFlowAndGetPayload("get-status-comments");

			assertEquals(statusComments.size(), commentIds.size());
			for (Comment comment : statusComments) {
				assertTrue(commentIds.contains(comment.getId()));
			}
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String messageId = (String) getTestRunMessageValue("messageId");
		deleteObject(messageId);
	}

}
