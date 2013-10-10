package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Link;

public class PublishLinkTestCases extends FacebookTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("publishLinkTestData");

			String profileId = getProfileId();
			testObjects.put("profileId", profileId);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({ RegressionTests.class })
	@Test
	public void testPublishLink() {
		try {
			MessageProcessor flow = lookupFlowConstruct("publish-link");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			String messageId = (String) response.getMessage().getPayload();
			
			testObjects.put("messageId", messageId);
			Link resultLink = getLink(messageId);
			
			assertTrue(messageId.equals(resultLink.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String profileId = (String) testObjects.get("profileId");
			String messageId = (String) testObjects.get("messageId");
			deleteObject(profileId + "_" + messageId);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}
}
