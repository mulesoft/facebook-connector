package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class PublishMessageTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Category({SmokeTests.class,RegressionTests.class})
	@Test
	public void testPublishMessage() {
		testObjects = (HashMap<String, Object>) context
				.getBean("publishMessageTestData");


		try {
			// TODO: get object back to check this is correct.		
			assertNotNull(publishMessage(testObjects.get("profileId").toString(), testObjects.get("msg").toString()));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
