/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Page;

public class GetPageTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String,Object>) getBeanFromContext("getPageTestData");
	}
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetPage() {
    	try {
    		String pageId = (String) testObjects.get("page");
    		
    		MessageProcessor flow = lookupFlowConstruct("get-page");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			Page page = (Page) response.getMessage().getPayload();
			assertEquals(pageId, page.getId());
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
     
	}
    
}