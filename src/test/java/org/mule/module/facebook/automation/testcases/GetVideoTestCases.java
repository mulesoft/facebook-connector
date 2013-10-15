package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Video;

public class GetVideoTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getVideoTestData");
			
		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetVideo() {
		try {
			Video video = runFlowAndGetPayload("get-video");
			assertNotNull(video);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
}
