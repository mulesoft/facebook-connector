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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GetPostCommentsTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getPostCommentsTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);

        String msg = (String) getTestRunMessageValue("msg");
        String messageId = publishMessage(profileId, msg);
        upsertOnTestRunMessage("messageId", messageId);

        List<String> comments = (List<String>) getTestRunMessageValue("comments");
        List<String> commentIds = new ArrayList<String>();
        for (String comment : comments) {
            String commentId = publishComment(messageId, comment);
            commentIds.add(commentId);
        }

        upsertOnTestRunMessage("commentIds", commentIds);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetPostComments() {
        try {
            String messageId = (String) getTestRunMessageValue("messageId");
            upsertOnTestRunMessage("post", messageId);
            List<String> commentIds = (List<String>) getTestRunMessageValue("commentIds");

            List<Comment> postComments = runFlowAndGetPayload("get-post-comments");

            assertEquals(postComments.size(), commentIds.size());
            for (Comment comment : postComments) {
                assertTrue(commentIds.contains(comment.getId()));
            }
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String messageId = (String) getTestRunMessageValue("messageId");
        deleteObject(messageId);
    }

}
