package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

public class SetPagePictureFromLinkTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("setPagePictureFromLinkTestData");
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testSetPagePictureFromLink() {
		try {
			Boolean result = runFlowAndGetPayload("set-page-picture-from-link");
			assertTrue(result);
		}
		catch (Exception e) {
			fail("Test failed. Please make sure that you have less than 1000 photos in your page's Profile Picture album.\n" + ConnectorTestUtils.getStackTrace(e));
		}
	}

}
