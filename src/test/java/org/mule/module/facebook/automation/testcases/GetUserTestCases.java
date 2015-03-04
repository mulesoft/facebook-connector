/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GetUserTestCases extends FacebookTestParent {

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getUserTestData");

        User loggedInUser = getLoggedUserDetails();
        upsertOnTestRunMessage("username", loggedInUser.getId());
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetUser() {
        try {
            User user = runFlowAndGetPayload("get-user");
            assertEquals(user.getId(), (String) getTestRunMessageValue("username"));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

}
