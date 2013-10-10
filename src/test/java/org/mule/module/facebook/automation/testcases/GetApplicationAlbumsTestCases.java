/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Album;

public class GetApplicationAlbumsTestCases extends FacebookTestParent {
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetApplicationAlbums() {
		try {
			testObjects = (HashMap<String,Object>) context.getBean("getApplicationAlbumsTestData");

			MessageProcessor flow = lookupFlowConstruct("get-application-albums");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			List<Album> albums = (List<Album>) response.getMessage().getPayload();
			assertNotNull(albums);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
     
	}
    
}