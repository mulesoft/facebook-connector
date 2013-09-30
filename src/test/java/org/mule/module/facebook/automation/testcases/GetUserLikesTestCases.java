package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.PageConnection;

public class GetUserLikesTestCases extends FacebookTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getUserLikesTestData");
			
			String profileId = getProfileId();
			testObjects.put("profileId", profileId);
			
			List<String> pagesToLike = (List<String>) testObjects.get("pagesToLike"); 
			for (String page : pagesToLike) {
				like(page);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetUserLikes() {
		try {
			String profileId = (String) testObjects.get("profileId");
			testObjects.put("user", profileId);
			List<String> pagesToLike = (List<String>) testObjects.get("pagesToLike");
			
			MessageProcessor flow = lookupFlowConstruct("get-user-likes");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<PageConnection> likes = (List<PageConnection>) response.getMessage().getPayload();
			assertEquals(pagesToLike.size(), likes.size());
			
			for (PageConnection like : likes) {
				assertTrue(pagesToLike.contains(like.getId()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			List<String> pagesToLike = (List<String>) testObjects.get("pagesToLike");
			for (String page : pagesToLike) {
				dislike(page);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
