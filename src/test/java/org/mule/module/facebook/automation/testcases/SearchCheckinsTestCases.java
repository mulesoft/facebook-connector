package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Post;

public class SearchCheckinsTestCases extends FacebookTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context.getBean("searchCheckinsTestData");
			String profileId = getProfileId();
			testObjects.put("profileId", profileId);
			
			// Check-in at Pizza place
			String messageId = publishMessage(profileId, "I like pizza", null, null, null, null, null, "132738745815");
			testObjects.put("messageId", messageId);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testSearchCheckins() {
		try {
			MessageProcessor flow = lookupFlowConstruct("search-checkins");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			Collection<String> searchResponse = (Collection<String>)  response.getMessage().getPayload();

			assertEquals(searchResponse.isEmpty(), false);
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
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}