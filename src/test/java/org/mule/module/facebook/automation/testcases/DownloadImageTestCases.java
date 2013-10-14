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

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.facebook.types.Photo;
import org.mule.modules.tests.ConnectorTestUtils;

public class DownloadImageTestCases extends FacebookTestParent {
	
	@Before
	public void setUp() throws Exception  {
		testObjects = (HashMap<String,Object>) getBeanFromContext("downloadImageTestData");

		String profileId = getProfileId();
			
		String caption = (String) testObjects.get("caption");
		File photoFile = new File(getClass().getClassLoader().getResource((String) testObjects.get("photoFilePath")).toURI());

		String photoId = publishPhoto(profileId, caption, photoFile);
		testObjects.put("photoId", photoId);
	}
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testDownloadImage() {
    	try {
    		String photoId = (String) testObjects.get("photoId");
    		Photo photo = getPhoto(photoId, "0");
    		
    		testObjects.put("imageUri", photo.getPicture());
    		MessageProcessor flow = lookupFlowConstruct("download-image");
    	
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			byte[] result = (byte[]) response.getMessage().getPayload();
			assertTrue(result.length > 0);
			
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(result));
			assertTrue(image.getHeight() == photo.getHeight());
			assertTrue(image.getWidth() == photo.getWidth());
    	} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
    
    @After
    public void tearDown() throws Exception {
   		String photoId = (String) testObjects.get("photoId");
   		deleteObject(photoId);
    }
    
}