/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Photo.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class GetPhotoTagsTestCases extends FacebookTestParent {

    @Before
    public void setUp() throws Exception {

        initializeTestRunMessage("getPhotoTagsTestData");

        String profileId = getProfileId();
        String auxProfileId = getProfileIdAux();

        upsertOnTestRunMessage("profileId", profileId);
        upsertOnTestRunMessage("auxProfileId", auxProfileId);

        String photoFilePath = getTestRunMessageValue("photoFilePath");
        String caption = getTestRunMessageValue("caption");

        File photo = new File(getClass().getClassLoader().getResource(photoFilePath).toURI());

        String photoId = publishPhoto(profileId, caption, photo);
        upsertOnTestRunMessage("photoId", photoId);

        tagPhoto(photoId, auxProfileId);
    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testGetPhotoTags() {
        try {
            String auxProfileId = getTestRunMessageValue("auxProfileId");
            String photoId = getTestRunMessageValue("photoId");

            List<Tag> photoTags = getPhotoTags(photoId);

            assertTrue(photoTags.size() == 1);

            Tag tag = photoTags.get(0);
            assertEquals(tag.getId(), auxProfileId);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String photoId = getTestRunMessageValue("photoId");
        deleteObject(photoId);
    }

}
