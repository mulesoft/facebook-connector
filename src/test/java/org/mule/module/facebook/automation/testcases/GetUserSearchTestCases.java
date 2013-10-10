package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Post;

public class GetUserSearchTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("getUserSearchTestData");
			
			String profileId = getProfileId();
			testObjects.put("user", profileId);
			
			String messageId = publishMessage(profileId, (String) testObjects.get("msg"));
			testObjects.put("objectId", messageId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserSearch() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-user-search");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<Post> result = (List<Post>) response.getMessage().getPayload();
			
			String messageId = (String) testObjects.get("objectId");
			boolean found = false;
			for(Post post : result) {
				if(messageId.equals(post.getId())) {
					found = true;
					break;
				}
 			}
			
			assertTrue(found);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			deleteObject((String) testObjects.get("objectId"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
