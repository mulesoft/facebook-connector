package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
		initializeTestRunMessage("publishMessageTestData");

		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
	}

	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testPublishMessage() {
		try {
			String profileId = (String) getTestRunMessageValue("profileId");
			String msg = (String) getTestRunMessageValue("msg");

			String messageId = publishMessage(profileId, msg);
			upsertOnTestRunMessage("messageId", messageId);
			
			StatusMessage status = getStatus(messageId);

			assertEquals(messageId, status.getId());
			assertEquals(msg, status.getMessage());

		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}

	@After
	public void tearDown() throws Exception {
		String messageId = (String) getTestRunMessageValue("messageId");
		deleteObject(messageId);
	}
}
