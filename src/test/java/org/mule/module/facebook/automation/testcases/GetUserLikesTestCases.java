package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.PageConnection;

public class GetUserLikesTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getUserLikesTestData");
			
			String profileId = getProfileId();
			testObjects.put("user", profileId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserLikes() {
		try {
			
			MessageProcessor flow = lookupFlowConstruct("get-user-likes");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<PageConnection> likes = (List<PageConnection>) response.getMessage().getPayload();
			
			assertNotNull(likes);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}