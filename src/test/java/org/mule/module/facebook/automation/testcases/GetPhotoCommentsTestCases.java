/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Comment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetPhotoCommentsTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getPhotoCommentsTestData");
        String profileId = getProfileId();
        upsertOnTestRunMessage("user", profileId);

        String caption = (String) getTestRunMessageValue("caption");
        File photoFile = new File(getClass().getClassLoader().getResource((String) getTestRunMessageValue("photoFilePath")).toURI());

        String photoId = publishPhoto(profileId, caption, photoFile);
        upsertOnTestRunMessage("photo", photoId);

        String commentId = publishComment((String) getTestRunMessageValue("photo"), (String) getTestRunMessageValue("msg"));
        upsertOnTestRunMessage("commentId", commentId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetPhotoComments() {
        try {
            List<Comment> result = runFlowAndGetPayload("get-photo-comments");

            assertTrue(result.size() == 1);
            Comment comment = result.get(0);
            assertTrue(comment.getId().equals((String) getTestRunMessageValue("commentId")));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String commentId = (String) getTestRunMessageValue("commentId");
        deleteObject(commentId);
        String photoId = (String) getTestRunMessageValue("photo");
        deleteObject(photoId);
    }

}