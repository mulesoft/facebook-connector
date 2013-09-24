package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class PublishEventTestCases extends FacebookTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = new HashMap<String, Object>();
			
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
			MessageProcessor flow = lookupFlowConstruct("publish-event");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
