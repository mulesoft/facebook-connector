/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;
import org.springframework.util.StringUtils;

public class PublishVideoTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("publishVideoTestData");

		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testPublishVideo() {
		try {
			String profileId = getTestRunMessageValue("profileId");
			String title = getTestRunMessageValue("title");
			String description = getTestRunMessageValue("description");
			String videoFilePath = getTestRunMessageValue("videoFilePath");
			
			File video = new File(getClass().getClassLoader().getResource(videoFilePath).toURI());
			
			String videoId = publishVideo(profileId, title, description, video);
			assertFalse(StringUtils.isEmpty(videoId));
			
			upsertOnTestRunMessage("videoId", videoId);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String videoId = getTestRunMessageValue("videoId");
		deleteObject(videoId);
	}
	
}
