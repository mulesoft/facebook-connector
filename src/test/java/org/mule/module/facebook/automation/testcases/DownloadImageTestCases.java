/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.facebook.types.Photo;
import org.mule.modules.tests.ConnectorTestUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DownloadImageTestCases extends FacebookTestParent {

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("downloadImageTestData");

        String profileId = getProfileId();

        String caption = (String) getTestRunMessageValue("caption");
        File photoFile = new File(getClass().getClassLoader().getResource((String) getTestRunMessageValue("photoFilePath")).toURI());

        String photoId = publishPhoto(profileId, caption, photoFile);
        Thread.sleep(5000);
        upsertOnTestRunMessage("photoId", photoId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testDownloadImage() {
        try {
            String photoId = (String) getTestRunMessageValue("photoId");
            Photo photo = getPhoto(photoId, "0");

            String imageUri = photo.getSource();
            Thread.sleep(5000);

            upsertOnTestRunMessage("imageUri", imageUri);

            byte[] result = runFlowAndGetPayload("download-image");
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
        String photoId = (String) getTestRunMessageValue("photoId");
        deleteObject(photoId);
    }

}