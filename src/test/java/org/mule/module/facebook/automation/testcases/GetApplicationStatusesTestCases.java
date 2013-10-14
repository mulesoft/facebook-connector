package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.StatusMessage;

public class GetApplicationStatusesTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) getBeanFromContext("getApplicationStatusesTestData");
			
		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetApplicationStatuses() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-application-statuses");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<StatusMessage> statusMessages = (List<StatusMessage>) response.getMessage().getPayload();
			assertTrue(statusMessages.isEmpty());
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {

	}
}
