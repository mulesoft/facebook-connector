package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class InviteUserTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("inviteUserTestData");
		
		String profileId = getProfileId();
		String auxProfileId = getProfileIdAux();
		upsertOnTestRunMessage("profileId", profileId);
		upsertOnTestRunMessage("auxProfileId", auxProfileId);
			
		String eventName = getTestRunMessageValue("eventName");
		String startTime = getTestRunMessageValue("startTime");
		
		String eventId = publishEvent(profileId, eventName, startTime);
		upsertOnTestRunMessage("eventId", eventId);
		
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testInviteUser() {
		try {
			String auxProfileId = getTestRunMessageValue("auxProfileId");
			String eventId = getTestRunMessageValue("eventId");

			Boolean result = inviteUser(eventId, auxProfileId);
			assertTrue(result);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String eventId = getTestRunMessageValue("eventId");
		deleteObject(eventId);
	}
	
}
