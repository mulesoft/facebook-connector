/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DislikeTestCases extends FacebookTestParent {

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("dislikeTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);

        String msg = (String) getTestRunMessageValue("msg");
        String messageId = publishMessage(profileId, msg);
        upsertOnTestRunMessage("messageId", messageId);

        like(messageId);
    }

    @Category({RegressionTests.class})
    @Test
    public void testDislike() {
        try {
            String messageId = (String) getTestRunMessageValue("messageId");
            upsertOnTestRunMessage("postId", messageId);

            Boolean result = runFlowAndGetPayload("dislike");
            assertTrue(result);
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
