package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.StatusMessage;

public class GetStatusTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) context.getBean("getStatusTestData");
		
		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
		
		String msg = (String) testObjects.get("msg");
		
		String messageId = publishMessage(profileId, msg);
		testObjects.put("messageId", messageId);
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetStatus() {
		try {
			String msg = (String) testObjects.get("msg");
			String messageId = (String) testObjects.get("messageId");
			testObjects.put("status", messageId);
			
			MessageProcessor flow = lookupFlowConstruct("get-status");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			StatusMessage status = (StatusMessage) response.getMessage().getPayload();
			
			assertTrue(status.getId().equals(messageId));
			assertTrue(status.getMessage().equals(msg));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String messageId = (String) testObjects.get("messageId");
		deleteObject(messageId);
	}
	
}
