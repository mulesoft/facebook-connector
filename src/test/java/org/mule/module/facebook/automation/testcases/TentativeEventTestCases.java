package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.tests.ConnectorTestUtils;

public class TentativeEventTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) getBeanFromContext("tentativeEventTestData");
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testTentativeEvent() {
		try {
			MessageProcessor flow = lookupFlowConstruct("tentative-event");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Boolean result = (Boolean) response.getMessage().getPayload();
			assertTrue(result);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String eventId = (String) testObjects.get("eventId");
		declineEvent(eventId);
	}
	
}
