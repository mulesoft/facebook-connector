/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Photo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.io.File;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetAlbumPhotosTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getAlbumPhotosTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);

        String msg = (String) getTestRunMessageValue("msg");
        String albumName = (String) getTestRunMessageValue("albumName");

        String albumId = publishAlbum(albumName, msg, profileId);
        upsertOnTestRunMessage("album", albumId);

        String caption = (String) getTestRunMessageValue("caption");
        String photoFileName = (String) getTestRunMessageValue("photoFileName");

        File photo = new File(getClass().getClassLoader().getResource(photoFileName).toURI());
        String photoId = publishPhoto(albumId, caption, photo);

        upsertOnTestRunMessage("photoId", photoId);
    }


    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetAlbumPhotos() {
        try {
            final String photoId = (String) getTestRunMessageValue("photoId");

            Collection<Photo> photos = runFlowAndGetPayload("get-album-photos");

            Collection<Photo> matchingPhotos = CollectionUtils.select(photos, new Predicate() {

                @Override
                public boolean evaluate(Object object) {
                    Photo photo = (Photo) object;
                    return photo.getId().equals(photoId);
                }
            });

            assertTrue(matchingPhotos.size() == 1);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

    @After
    public void tearDown() throws Exception {
        deleteObject((String) getTestRunMessageValue("photoId"));
    }
}