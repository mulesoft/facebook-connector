package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.facebook.types.OutboxThread;
import org.mule.modules.tests.ConnectorTestUtils;

public class GetUserUpdatesTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("getUserUpdatesTestData");
		
		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
		upsertOnTestRunMessage("user", profileId);
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserUpdates() {
		try {
			List<OutboxThread> outboxThreads = runFlowAndGetPayload("get-user-updates");
			// Test user shouldn't have any updates
			assertTrue(outboxThreads.isEmpty());
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
}
