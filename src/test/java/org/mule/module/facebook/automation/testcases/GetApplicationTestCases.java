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

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Application;

public class GetApplicationTestCases extends FacebookTestParent {
	
    @SuppressWarnings("unchecked")
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testGetApplication() {
    	
    	testObjects = (HashMap<String,Object>) context.getBean("getApplicationTestData");
    	
		MessageProcessor flow = lookupFlowConstruct("get-application");
    	
		try {
			MuleEvent response = flow.process(getTestEvent(testObjects));
			Application app = (Application) response.getMessage().getPayload();
			
			assertEquals(testObjects.get("application"), app.getId());

		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
     
	}
    
}