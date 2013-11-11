package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

public class TentativeEventTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("tentativeEventTestData");

		String profileId = getProfileId();
    	String auxProfileId = getProfileIdAux();
    	upsertOnTestRunMessage("profileId", profileId);
    	upsertOnTestRunMessage("auxProfileId", auxProfileId);
    	
    	String eventName = getTestRunMessageValue("eventName");
    	String startTime = getTestRunMessageValue("startTime");

    	String eventId = publishEventAux(auxProfileId, eventName, startTime);
    	upsertOnTestRunMessage("eventId", eventId);
    	
    	inviteUserAux(eventId, profileId);
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testTentativeEvent() {
		try {
			Boolean result = runFlowAndGetPayload("tentative-event");
			assertTrue(result);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String eventId = (String) getTestRunMessageValue("eventId");
		deleteObjectAux(eventId);
	}
	
}
