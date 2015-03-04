/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Post;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@Ignore
public class GetUserTaggedTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getUserTaggedTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);
        String msg = (String) getTestRunMessageValue("msg");
        List<String> tagList = new ArrayList<String>();
        tagList.add(getProfileIdAux());
        upsertOnTestRunMessage("tags", tagList);
        String messageId = publishMessage(profileId, msg, null, null, null, null, null, "132738745815");
        upsertOnTestRunMessage("messageId", messageId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetUserTagged() {
        try {
            String profileId = (String) getTestRunMessageValue("profileId");
            upsertOnTestRunMessage("user", getProfileIdAux());

            List<Post> posts = runFlowAndGetPayload("get-user-tagged");

            boolean found = false;
            for (Post post : posts) {
                if (post.getFrom().getId().equals(profileId)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
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
