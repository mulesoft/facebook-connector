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

import com.restfb.types.Post;

public class GetApplicationWallTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) context.getBean("getApplicationWallTestData");
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetApplicationWall() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-application-wall");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<Post> result = (List<Post>) response.getMessage().getPayload();
			
			assertNotNull(result);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
