/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Post;

public class GetUserTaggedTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getUserTaggedTestData");
		
		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);

		String msg = (String) getTestRunMessageValue("msg");
		String messageId = publishMessage(profileId, msg);
		upsertOnTestRunMessage("messageId", messageId);
        List<String> tagList = new ArrayList<String>();
        tagList.add(getProfileIdAux());
        upsertOnTestRunMessage("tags", tagList);
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserTagged() {
		try {
			String profileId = (String) getTestRunMessageValue("profileId");
			
			List<Post> posts = runFlowAndGetPayload("get-user-tagged");
			
			boolean found = false;
			for (Post post : posts) {
				if (post.getFrom().getId().equals(profileId)) {
					found = true;
					break;
				}
			}
			assertTrue(found);
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
