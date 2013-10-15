package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

public class LikeTestCases extends FacebookTestParent {
	
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("likeTestData");
		
		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
		
		String msg = (String) getTestRunMessageValue("msg");
		String messageId = publishMessage(profileId, msg);
		upsertOnTestRunMessage("messageId", messageId);
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testLike() {
		try {
			String messageId = (String) getTestRunMessageValue("messageId");
			upsertOnTestRunMessage("postId", messageId);
			
			Boolean result = runFlowAndGetPayload("like");
			assertTrue(result);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String messageId = (String) getTestRunMessageValue("messageId");
		deleteObject(messageId);
	}


}
