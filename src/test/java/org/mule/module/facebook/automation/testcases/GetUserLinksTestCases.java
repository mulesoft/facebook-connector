package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
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
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Link;

public class GetUserLinksTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) getBeanFromContext("getUserLinksTestData");
			
		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
			
		String msg = (String) testObjects.get("msg");
		String link = (String) testObjects.get("link");
			
		String linkId = publishLink(profileId, msg, link);
		testObjects.put("linkId", linkId);
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserLinks() {
		try {
			String profileId = (String) testObjects.get("profileId");
			testObjects.put("user", profileId);
			
			String linkId = (String) testObjects.get("linkId");
			
			MessageProcessor flow = lookupFlowConstruct("get-user-links");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<Link> links = (List<Link>) response.getMessage().getPayload();
			// We only insert 1 link
			assertTrue(links.size() == 1);
			
			Link link = links.get(0);
			assertEquals(link.getId(), linkId);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String profileId = (String) testObjects.get("profileId");
		String linkId = (String) testObjects.get("linkId");
		deleteObject(profileId + "_" + linkId);
	}
	
}
