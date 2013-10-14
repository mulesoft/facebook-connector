package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.facebook.types.OutboxThread;
import org.mule.modules.tests.ConnectorTestUtils;

public class GetUserUpdatesTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) getBeanFromContext("getUserUpdatesTestData");
		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
		testObjects.put("user", profileId);
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserUpdates() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-user-updates");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<OutboxThread> outboxThreads = (List<OutboxThread>) response.getMessage().getPayload();
			// Test user shouldn't have any updates
			assertTrue(outboxThreads.isEmpty());
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
}
