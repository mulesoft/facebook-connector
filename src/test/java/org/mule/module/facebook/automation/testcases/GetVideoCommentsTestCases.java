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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GetVideoCommentsTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getVideoCommentsTestData");

        String commentId = publishComment((String) getTestRunMessageValue("postId"), (String) getTestRunMessageValue("msg"));
        upsertOnTestRunMessage("objectId", commentId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetVideoComments() {
        try {
            List<Comment> result = runFlowAndGetPayload("get-video-comments");

            Comment comment = result.get(0);
            assertEquals((String) getTestRunMessageValue("msg"), comment.getMessage());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String objectId = (String) getTestRunMessageValue("objectId");
        deleteObject(objectId);
    }

}
