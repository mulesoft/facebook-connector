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
import org.mule.module.facebook.FacebookConnectorUnitTest;

import com.restfb.types.Link;
import com.restfb.types.StatusMessage;

public class PublishLinkTestCases extends FacebookTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context
					.getBean("publishLinkTestData");

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
			String profileId = testObjects.get("profileId").toString();

			String msg = (String) testObjects.get("msg");
			String link = testObjects.get("link").toString();

			MessageProcessor flow = lookupFlowConstruct("publish-link");

			MuleEvent response = flow.process(getTestEvent(testObjects));
			String messageId = response.getMessage().getPayloadAsString();
			testObjects.put("messageId", messageId);

			Link resultLink = getLink(messageId);

			assertTrue(resultLink.getMessage().equals(msg));
			assertTrue(resultLink.getLink().equals(link));

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	
	//Removed because this is not working.
//	@After
//	public void tearDown() {
//
//		try {
//
//			deleteObject(testObjects.get("messageId").toString());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//
//	}
}
