/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Post;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
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

public class GetUserPostsTestCases extends FacebookTestParent {

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getUserPostsTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);

        List<String> messages = (List<String>) getTestRunMessageValue("messages");
        List<String> messageIds = new ArrayList<String>();

        for (String message : messages) {
            String messageId = publishMessage(profileId, message);
            Thread.sleep(2000);
            messageIds.add(messageId);
            upsertOnTestRunMessage("messageIds", messageIds);
        }
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetUserPosts() {
        try {
            String profileId = (String) getTestRunMessageValue("profileId");
            upsertOnTestRunMessage("user", profileId);

            final List<String> messageIds = (List<String>) getTestRunMessageValue("messageIds");

            Collection<Post> posts = runFlowAndGetPayload("get-user-posts");

            // Assert that the inserted posts can be found in the user's list of posts
            Collection<Post> matching = CollectionUtils.select(posts, new Predicate() {

                @Override
                public boolean evaluate(Object object) {
                    Post post = (Post) object;
                    return messageIds.contains(post.getId());
                }

            });

            assertTrue(matching.size() == messageIds.size());

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
