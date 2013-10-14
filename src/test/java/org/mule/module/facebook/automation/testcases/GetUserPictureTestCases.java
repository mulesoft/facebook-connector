package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.tests.ConnectorTestUtils;

public class GetUserPictureTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) getBeanFromContext("getUserPictureTestData");
			
		String profileId = getProfileId();
		testObjects.put("user", profileId);
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetUserPicture() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-user-picture");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			byte[] result = (byte[]) response.getMessage().getPayload();
			assertTrue(result.length != 0);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
}
