package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.StatusMessage;

public class PublishMessageTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("publishMessageTestData");

			String profileId = getProfileId();
			testObjects.put("profileId", profileId);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testPublishMessage() {
		try {
			String profileId = (String) testObjects.get("profileId");
			String msg = (String) testObjects.get("msg");

			String messageId = publishMessage(profileId, msg);
			testObjects.put("messageId", messageId);
			
			StatusMessage status = getStatus(messageId);

			assertEquals(messageId, status.getId());
			assertEquals(msg, status.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void tearDown() {
		try {
			String messageId = (String) testObjects.get("messageId");
			deleteObject(messageId);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}
}
