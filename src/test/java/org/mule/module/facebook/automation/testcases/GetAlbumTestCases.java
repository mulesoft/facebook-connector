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

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Album;

public class GetAlbumTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
	    	testObjects = (HashMap<String,Object>) context.getBean("getAlbumTestData");
			String profileId = getProfileId();
			testObjects.put("profileId", profileId);
			String id = publishAlbum((String) testObjects.get("albumName"), (String) testObjects.get("msg"), (String) testObjects.get("profileId"));
			testObjects.put("album", id);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetAlbum() {
		MessageProcessor flow = lookupFlowConstruct("get-album");
    	
		try {

			MuleEvent response = flow.process(getTestEvent(testObjects));
			Album album = (Album) response.getMessage().getPayload();

			assertTrue(album instanceof Album);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
     
	}
    
}