package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

public class GetUserPictureTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getUserPictureTestData");
			
		String profileId = getProfileId();
		upsertOnTestRunMessage("user", profileId);
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetUserPicture() {
		try {
			byte[] result = runFlowAndGetPayload("get-user-picture");
			assertTrue(result.length != 0);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
}
