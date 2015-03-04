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

public class GetUserTelevisionTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getUserTelevisionTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);

        List<String> expectedIds = getExpectedTelevision();
        upsertOnTestRunMessage("expectedIds", expectedIds);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetUserTelevision() {
        try {
            String profileId = (String) getTestRunMessageValue("profileId");
            List<String> expectedIds = getTestRunMessageValue("expectedIds");

            assertTrue("Please make sure that you have liked a TV page on your Facebook account before running this test.", !expectedIds.isEmpty());

            upsertOnTestRunMessage("user", profileId);

            List<PageConnection> pageConnections = runFlowAndGetPayload("get-user-television");
            for (PageConnection pageConnection : pageConnections) {
                assertTrue(expectedIds.contains(pageConnection.getId()));
            }

            assertEquals(expectedIds.size(), pageConnections.size());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
