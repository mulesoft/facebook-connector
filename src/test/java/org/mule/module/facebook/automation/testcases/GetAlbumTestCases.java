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

import com.restfb.types.Album;

public class GetAlbumTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
    	testObjects = (HashMap<String,Object>) context.getBean("getAlbumTestData");
		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
		String id = publishAlbum((String) testObjects.get("albumName"), (String) testObjects.get("msg"), (String) testObjects.get("profileId"));
		testObjects.put("album", id);
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetAlbum() {
		try {
			String id = (String) testObjects.get("album");
			String albumName = (String) testObjects.get("albumName");
			
			MessageProcessor flow = lookupFlowConstruct("get-album");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			Album album = (Album) response.getMessage().getPayload();

			assertEquals(album.getId(), id);
			assertEquals(album.getName(), albumName);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
     
	}
    
}