package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.StatusMessage;

public class PublishMessageTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String, Object>) context.getBean("publishMessageTestData");

		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
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
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}

	@After
	public void tearDown() throws Exception {
		String messageId = (String) testObjects.get("messageId");
		deleteObject(messageId);
	}
}
