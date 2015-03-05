/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.NamedFacebookType;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/*
 * Account and Auxiliary Account MUST BE FRIENDS
 */
public class GetUserFriendsTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getUserFriendsTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("user", profileId);

        String auxProfileId = getProfileIdAux();
        upsertOnTestRunMessage("auxProfileId", auxProfileId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetUserFriends() {
        try {
            String auxProfileId = getTestRunMessageValue("auxProfileId");

            List<NamedFacebookType> result = runFlowAndGetPayload("get-user-friends");
            assertEquals(2, result.size());

            NamedFacebookType friend = result.get(0);
            assertEquals(friend.getId(), auxProfileId);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}