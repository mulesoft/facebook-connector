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

public class GetUserUpdatesTestCases extends FacebookTestParent {
	
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getUserUpdatesTestData");
			String profileId = getProfileId();
			testObjects.put("profileId", profileId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetUserUpdates() {
		try {
			String profileId = (String) testObjects.get("profileId");
			testObjects.put("user", profileId);
			
			MessageProcessor flow = lookupFlowConstruct("get-user-updates");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<OutboxThread> outboxThreads = (List<OutboxThread>) response.getMessage().getPayload();
			// Test user shouldn't have any updates
			assertTrue(outboxThreads.isEmpty());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
