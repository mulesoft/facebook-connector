/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Post;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GetPostTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getPostTestData");

        String profileId = getProfileId();
        String msg = (String) getTestRunMessageValue("msg");

        String messageId = publishMessage(profileId, msg);
        upsertOnTestRunMessage("messageId", messageId);
    }

    @Category({RegressionTests.class})
    @Test
    public void testGetPost() {
        try {
            String message = (String) getTestRunMessageValue("msg");
            String messageId = (String) getTestRunMessageValue("messageId");
            upsertOnTestRunMessage("post", messageId);

            Post post = runFlowAndGetPayload("get-post");
            assertEquals(post.getId(), messageId);
            assertEquals(post.getMessage(), message);
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
