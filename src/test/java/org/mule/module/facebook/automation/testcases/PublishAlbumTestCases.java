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

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PublishAlbumTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String,Object>) context.getBean("publishAlbumTestData");
			String profileId = getProfileId();
			testObjects.put("profileId", profileId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testPublishAlbum() {
		try {
			String albumId = publishAlbum((String) testObjects.get("albumName"), (String) testObjects.get("msg"), (String) testObjects.get("profileId"));
			assertTrue(StringUtils.isNotEmpty(albumId));
			
			testObjects.put("albumId", albumId);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

    @After
    public void tearDown() {
    	try {
    		String albumId = (String) testObjects.get("albumId");
    		
    		// Deletion of albums cannot be done by the Facebook API.
//    		deleteObject(albumId);
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		fail();
    	}
    }
    
}