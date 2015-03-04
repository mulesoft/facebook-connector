/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Link;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.*;

public class GetUserLinksTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getUserLinksTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);

        String msg = (String) getTestRunMessageValue("msg");
        String link = (String) getTestRunMessageValue("link");

        String linkId = publishLink(profileId, msg, link);
        upsertOnTestRunMessage("linkId", linkId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetUserLinks() {
        try {
            String profileId = (String) getTestRunMessageValue("profileId");
            upsertOnTestRunMessage("user", profileId);

            String linkId = (String) getTestRunMessageValue("linkId");

            List<Link> links = runFlowAndGetPayload("get-user-links");
            // We only insert 1 link
            assertTrue(links.size() == 1);

            Link link = links.get(0);
            assertEquals(link.getId(), linkId);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String profileId = (String) getTestRunMessageValue("profileId");
        String linkId = (String) getTestRunMessageValue("linkId");
        deleteObject(profileId + "_" + linkId);
    }

}
