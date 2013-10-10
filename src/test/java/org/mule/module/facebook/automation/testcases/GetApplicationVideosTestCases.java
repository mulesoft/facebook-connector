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

import com.restfb.types.Video;

public class GetApplicationVideosTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) context.getBean("getApplicationVideosTestData");
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetApplicationVideos() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-application-videos");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<Video> result = (List<Video>) response.getMessage().getPayload();
			
			assertNotNull(result);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
