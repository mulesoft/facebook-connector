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

public class GetLinkCommentsTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getLinkCommentsTestData");
        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);

        String msg = getTestRunMessageValue("msg").toString();
        // link is a url here
        String link = getTestRunMessageValue("link").toString();

        String messageId = publishLink(profileId, msg, link);
        upsertOnTestRunMessage("messageId", messageId);

        String commentId = publishComment(messageId, (String) getTestRunMessageValue("commentMsg"));
        upsertOnTestRunMessage("commentId", commentId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetLinkComments() {
        try {
            String messageId = (String) getTestRunMessageValue("messageId");
            String commentMsg = (String) getTestRunMessageValue("commentMsg");
            String commentId = (String) getTestRunMessageValue("commentId");

            // link is the graph object id here
            upsertOnTestRunMessage("link", messageId);

            List<Comment> comments = runFlowAndGetPayload("get-link-comments");

            assertTrue(comments.size() == 1);
            Comment comment = comments.get(0);
            assertEquals(comment.getMessage(), commentMsg);
            assertEquals(comment.getId(), commentId);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String profileId = (String) getTestRunMessageValue("profileId");
        String messageId = (String) getTestRunMessageValue("messageId");
        deleteObject(profileId + "_" + messageId);
    }

}
