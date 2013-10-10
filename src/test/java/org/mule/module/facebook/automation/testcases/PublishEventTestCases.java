package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PublishEventTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
    	testObjects = (HashMap<String,Object>) context.getBean("publishEventTestData");
			
		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testPublishEvent() {
		try {
			String profileId = (String) testObjects.get("profileId");
			String eventName = (String) testObjects.get("eventName");
			String startTime = (String) testObjects.get("startTime");
			
			String eventId = publishEvent(profileId, eventName, startTime);
			testObjects.put("objectId", eventId);
			
			assertTrue(StringUtils.isNotEmpty(eventId));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() throws Exception {
		deleteObject((String) testObjects.get("objectId"));
	}
}
