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

import java.util.Collection;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Album;
import com.restfb.types.Photo;

public class GetAlbumPhotosTestCases extends FacebookTestParent {
	
	@Before
	public void setUp() {
		try {
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetAlbumPhotos() {
    	
    	testObjects = (HashMap<String,Object>) context.getBean("getAlbumPhotosTestData");
    	
		MessageProcessor flow = lookupFlowConstruct("get-album-photos");
    	
		try {
			Collection<Album> albums = requestUserAlbums((String) testObjects.get("user"),
					(String) testObjects.get("since"),
					(String) testObjects.get("until"),
					(String) testObjects.get("limit"),
					(String) testObjects.get("offset"));
			
			Album album = (Album) albums.toArray()[0];
			
			testObjects.put("album", album.getId());

			MuleEvent response = flow.process(getTestEvent(testObjects));
			Collection<Photo> photos = (Collection<Photo>) response.getMessage().getPayload();
			
			assertTrue(photos.size() != 0);

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
     
	}
    
}