package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PublishMessageTestCases extends FacebookTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("publishMessageTestData");
			
			String profileId = getProfileId();
			testObjects.put("profileId", profileId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class,RegressionTests.class})
	@Test
	public void testPublishMessage() {
		try {
			String profileId = (String) testObjects.get("profileId");
			String msg = (String) testObjects.get("msg");
			
			String messageId = publishMessage(profileId, msg);
			assertTrue(StringUtils.isNotEmpty(messageId));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
