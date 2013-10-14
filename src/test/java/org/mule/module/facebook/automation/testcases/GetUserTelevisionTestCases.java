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
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.PageConnection;

public class GetUserTelevisionTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) getBeanFromContext("getUserTelevisionTestData");
			
		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserTelevision() {
		try {
			String profileId = (String) testObjects.get("profileId");
			
			testObjects.put("user", profileId);
			
			MessageProcessor flow = lookupFlowConstruct("get-user-television");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<PageConnection> pageConnections = (List<PageConnection>) response.getMessage().getPayload();
			assertTrue(pageConnections.isEmpty());
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
}
