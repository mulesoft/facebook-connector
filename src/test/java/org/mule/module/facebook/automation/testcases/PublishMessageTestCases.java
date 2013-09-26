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
			testObjects = (HashMap<String, Object>) context
					.getBean("publishMessageTestData");

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

			String msg = (String) testObjects.get("msg");

			String messageId = publishMessage(testObjects.get("profileId")
					.toString(), msg);
			testObjects.put("messageId", messageId);

			testObjects.put("status", messageId);

			MessageProcessor flow = lookupFlowConstruct("get-status");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			StatusMessage status = (StatusMessage) response.getMessage()
					.getPayload();


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

			deleteObject(testObjects.get("messageID").toString());

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}
}
