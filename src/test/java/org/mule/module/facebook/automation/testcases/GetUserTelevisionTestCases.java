package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.PageConnection;

public class GetUserTelevisionTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getUserTelevisionTestData");
			
		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
		
		List<String> expectedIds = getExpectedTelevision();
		upsertOnTestRunMessage("expectedIds", expectedIds);
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserTelevision() {
		try {
			String profileId = (String) getTestRunMessageValue("profileId");
			List<String> expectedIds = getTestRunMessageValue("expectedIds");

			assertTrue("Please make sure that you have liked a TV page on your Facebook account before running this test.", !expectedIds.isEmpty());
			
			upsertOnTestRunMessage("user", profileId);
			
			List<PageConnection> pageConnections = runFlowAndGetPayload("get-user-television");
			for (PageConnection pageConnection : pageConnections) {
				assertTrue(expectedIds.contains(pageConnection.getId()));
			}
			
			assertEquals(expectedIds.size(), pageConnections.size());
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
}
