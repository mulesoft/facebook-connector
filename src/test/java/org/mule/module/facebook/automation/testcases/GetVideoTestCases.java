package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Video;

public class GetVideoTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) context.getBean("getVideoTestData");
			
		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetVideo() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-video");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Video video = (Video) response.getMessage().getPayload();
			
			assertNotNull(video);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
}
