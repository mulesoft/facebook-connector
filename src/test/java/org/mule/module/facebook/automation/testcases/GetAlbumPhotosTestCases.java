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

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Photo;

public class GetAlbumPhotosTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String,Object>) context.getBean("getAlbumPhotosTestData");
			
		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
			
		String msg = (String) testObjects.get("msg");
		String albumName = (String) testObjects.get("albumName");
			
		String albumId = publishAlbum(albumName, msg, profileId);
		testObjects.put("album", albumId);
			
		String caption = (String) testObjects.get("caption");
		String photoFileName = (String) testObjects.get("photoFileName");
			
		File photo = new File(getClass().getClassLoader().getResource(photoFileName).toURI());
		String photoId = publishPhoto(albumId, caption, photo);
			
		testObjects.put("photoId", photoId);
	}
	
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetAlbumPhotos() {
		try {
			final String photoId = (String) testObjects.get("photoId");
			
			MessageProcessor flow = lookupFlowConstruct("get-album-photos");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			Collection<Photo> photos = (Collection<Photo>) response.getMessage().getPayload();

			Collection<Photo> matchingPhotos = CollectionUtils.select(photos, new Predicate() {
				
				@Override
				public boolean evaluate(Object object) {
					Photo photo = (Photo) object;
					return photo.getId().equals(photoId);
				}
			});
			
			assertTrue(matchingPhotos.size() == 1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
     
	}
    
    @After
	public void tearDown() throws Exception {
		deleteObject((String) testObjects.get("photoId"));
		deleteObject((String) testObjects.get("album"));
	}
}