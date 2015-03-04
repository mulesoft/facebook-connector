/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Comment;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetAlbumCommentsTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getAlbumCommentsTestData");

        String albumName = (String) getTestRunMessageValue("albumName");
        String msg = (String) getTestRunMessageValue("msg");
        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);

        String albumId = publishAlbum(albumName, msg, profileId);
        upsertOnTestRunMessage("albumId", albumId);

        String commentMsg = (String) getTestRunMessageValue("commentMsg");
        String commentId = publishComment(albumId, commentMsg);
        upsertOnTestRunMessage("commentId", commentId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetAlbumComments() {
        try {
            String albumId = (String) getTestRunMessageValue("albumId");
            upsertOnTestRunMessage("album", albumId);
            final String commentId = (String) getTestRunMessageValue("commentId");

            Collection<Comment> comments = runFlowAndGetPayload("get-album-comments");

            Collection<Comment> matching = CollectionUtils.select(comments, new Predicate() {

                @Override
                public boolean evaluate(Object object) {
                    Comment comment = (Comment) object;
                    return comment.getId().equals(commentId);
                }
            });

            assertTrue(matching.size() == 1);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

}