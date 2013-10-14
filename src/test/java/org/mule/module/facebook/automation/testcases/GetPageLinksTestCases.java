/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Link;

public class GetPageLinksTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String,Object>) getBeanFromContext("getPageLinksTestData");
		
		String page = (String) testObjects.get("page");
		String message = (String) testObjects.get("message");
		String link = (String) testObjects.get("link");
		
		String linkId = publishLink(page, message, link);
		testObjects.put("linkId", linkId);
	}
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetPageLinks() {
		try {
			String linkId = (String) testObjects.get("linkId");
			
			MessageProcessor flow = lookupFlowConstruct("get-page-links");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			List<Link> links = (List<Link>) response.getMessage().getPayload();
			assertTrue(links.size() == 1);
			
			Link link = links.get(0);
			assertTrue(link.getId().equals(linkId));
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
    
    @After
    public void tearDown() throws Exception {
    	String pageId = (String) testObjects.get("page");
    	String linkId = (String) testObjects.get("linkId");
    	deleteObject(pageId + "_" + linkId);
    }
    
}