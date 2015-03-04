/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.PageConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.*;

public class GetUserLikesTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getUserLikesTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("user", profileId);

        List<String> expectedIds = getExpectedLikes();
        upsertOnTestRunMessage("expected", expectedIds);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetUserLikes() {
        try {
            List<String> expectedIds = getTestRunMessageValue("expected");

            assertTrue("Please make sure that you have liked a page on your Facebook account before running this test.", !expectedIds.isEmpty());

            List<PageConnection> result = runFlowAndGetPayload("get-user-likes");
            for (PageConnection pageConnection : result) {
                String pageId = pageConnection.getId();
                assertTrue(expectedIds.contains(pageId));
            }

            assertEquals(expectedIds.size(), result.size());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
