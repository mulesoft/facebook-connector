/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Photo;

public class GetPagePhotosTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String,Object>) context.getBean("getPagePhotosTestData");
	}
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetPagePhotos() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-page-photos");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			List<Photo> result = (List<Photo>) response.getMessage().getPayload();
			assertTrue(result.size() > 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
    
}