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

import java.util.List;

import static org.junit.Assert.*;

public class PublishCommentTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        //create comment for message
        initializeTestRunMessage("publishCommentTestData");

        String statusMsg = (String) getTestRunMessageValue("msg");
        String postId = publishMessage(getProfileId(), statusMsg);
        upsertOnTestRunMessage("postId", postId);
    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testPublishComment() {
        try {
            String postId = (String) getTestRunMessageValue("postId");
            String msg = (String) getTestRunMessageValue("msg");

            String commentId = runFlowAndGetPayload("publish-comment");

            List<Comment> comments = getStatusComments(postId);
            boolean commentFound = false;
            for (Comment comment : comments) {
                if (comment.getId().equals(commentId)) {
                    commentFound = true;
                    assertEquals(msg, comment.getMessage());
                    break;
                }
            }

            assertTrue("Comment not found", commentFound);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

    @After
    public void tearDown() throws Exception {
        deleteObject(getTestRunMessageValue("postId").toString());
    }

}
