/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.User;

public class PublishAlbumTestCases extends FacebookTestParent {
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testPublishAlbum() {
    	
    	testObjects = (HashMap<String,Object>) context.getBean("publishAlbumTestData");
    	
		try {
			publishAlbum((String) testObjects.get("albumName"), (String) testObjects.get("msg"), (String) testObjects.get("profileId"));
			
			
			// get back a HashMap: {albumName=albumForTest, profileId=100006563414301, msg=msgForTest}
			// i.e. void return type
			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
     
	}
    
}