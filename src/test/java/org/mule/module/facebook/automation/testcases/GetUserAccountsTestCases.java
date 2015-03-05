/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.facebook.types.GetUserAccountResponseType;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetUserAccountsTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getUserAccountsTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("user", profileId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetUserAccounts() {
        try {
            String pageId = getTestRunMessageValue("adminPageId");

            // User should be an admin of at least one page
            // This is implied by the facebook.page property in automation-credentials.properties
            List<GetUserAccountResponseType> result = runFlowAndGetPayload("get-user-accounts");
            assertTrue(result.size() >= 1);

            // Loop through every user account in an attempt to find the page with the ID specified
            // in automation-credentials.properties
            boolean found = false;
            for (GetUserAccountResponseType getUserAccountResponseType : result) {
                if (getUserAccountResponseType.getId().equals(pageId)) {
                    found = true;
                    break;
                }
            }

            // Assert that the page with the right ID has been found
            assertTrue(found);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}