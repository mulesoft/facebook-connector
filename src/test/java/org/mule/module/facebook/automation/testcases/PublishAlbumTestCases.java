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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Album;

public class PublishAlbumTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String,Object>) context.getBean("publishAlbumTestData");
		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testPublishAlbum() {
		try {
			String albumName = (String) testObjects.get("albumName");
			String msg = (String) testObjects.get("msg");
			String profileId = (String) testObjects.get("profileId");
			
			String albumId = publishAlbum(albumName, msg, profileId);
			testObjects.put("albumId", albumId);
			
			Album retrievedAlbum = getAlbum(albumId);
			assertEquals(retrievedAlbum.getId(), albumId);
			assertEquals(retrievedAlbum.getName(), albumName);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}

    @After
    public void tearDown() throws Exception {
    	String albumId = (String) testObjects.get("albumId");
    		
   	// 	Deletion of albums cannot be done by the Facebook API.
   	//	deleteObject(albumId);
    }
    
}