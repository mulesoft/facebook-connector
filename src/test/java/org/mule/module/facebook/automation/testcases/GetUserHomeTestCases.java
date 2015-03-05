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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetUserHomeTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getUserHomeTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("user", profileId);

        List<String> messages = (List<String>) getTestRunMessageValue("messages");
        List<String> messageIds = new ArrayList<String>();

        for (String message : messages) {
            String messageId = publishMessage(profileId, message);
            messageIds.add(messageId);
            Thread.sleep(2000);
        }

        upsertOnTestRunMessage("messageIds", messageIds);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetUserHome() {
        try {
            List<String> messageIds = (List<String>) getTestRunMessageValue("messageIds");

            Collection<Post> posts = runFlowAndGetPayload("get-user-home");
            Thread.sleep(5000);

            assertTrue(posts.size() >= messageIds.size());

            for (String messageId : messageIds) {
                boolean found = false;
                for (Post post : posts) {
                    if (post.getId().equals(messageId)) {
                        found = true;
                        break;
                    }
                }
                assertTrue(found);
            }
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        List<String> messageIds = (List<String>) getTestRunMessageValue("messageIds");
        for (String messageId : messageIds) {
            deleteObject(messageId);
        }
    }

}