package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PublishEventTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
	    	testObjects = (HashMap<String,Object>) context.getBean("publishEventTestData");
			
			String profileId = getProfileId();
			testObjects.put("profileId", profileId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testPublishEvent() {
		try {
			String eventId = publishEvent((String) testObjects.get("profileId"), (String) testObjects.get("eventName"), 
					(String) testObjects.get("startTime"));
			
			testObjects.put("objectId", eventId);
			
			assertNotNull(eventId);
			assertFalse("".equals(eventId));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			deleteObject((String) testObjects.get("objectId"));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
