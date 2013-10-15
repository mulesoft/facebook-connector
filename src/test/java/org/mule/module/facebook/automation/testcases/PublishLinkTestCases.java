package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Link;

public class PublishLinkTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("publishLinkTestData");

		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
	}

	@SuppressWarnings("unchecked")
	@Category({ RegressionTests.class })
	@Test
	public void testPublishLink() {
		try {
			String messageId = runFlowAndGetPayload("publish-link");

			upsertOnTestRunMessage("messageId", messageId);
			Link resultLink = getLink(messageId);
			
			assertTrue(messageId.equals(resultLink.getId()));
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String profileId = (String) getTestRunMessageValue("profileId");
		String messageId = (String) getTestRunMessageValue("messageId");
		deleteObject(profileId + "_" + messageId);
	}
}
