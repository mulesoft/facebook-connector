/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

public class SearchCheckinsTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("searchCheckinsTestData");
		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
			
		// Check-in at Pizza place
		String messageId = publishMessage(profileId, "I like pizza", null, null, null, null, null, "132738745815");
		upsertOnTestRunMessage("messageId", messageId);
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testSearchCheckins() {
		try {
			Collection<String> searchResponse = runFlowAndGetPayload("search-checkins");

			assertEquals(searchResponse.isEmpty(), false);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String messageId = (String) getTestRunMessageValue("messageId");
		deleteObject(messageId);
	}

}