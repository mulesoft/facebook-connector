/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Post.Likes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.io.File;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetPhotoLikesTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getPhotoLikesTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);

        String caption = (String) getTestRunMessageValue("caption");
        String photoFileName = (String) getTestRunMessageValue("photoFileName");

        File photo = new File(getClass().getClassLoader().getResource(photoFileName).toURI());
        String photoId = publishPhoto(profileId, caption, photo);

        // for "get-photo-likes"
        upsertOnTestRunMessage("photo", photoId);

        like(photoId);
    }

    @Category({RegressionTests.class})
    @Test
    public void testGetPhotoLikes() {
        try {
            Likes result = runFlowAndGetPayload("get-photo-likes");
            assertTrue(result.getData().size() == 1);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String photoId = (String) getTestRunMessageValue("photo");
        deleteObject(photoId);
    }

}