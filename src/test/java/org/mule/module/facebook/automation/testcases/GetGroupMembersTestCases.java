/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.facebook.types.Member;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetGroupMembersTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getGroupMembersTestData");

        String expectedGroupId = getExpectedGroupId();
        upsertOnTestRunMessage("group", expectedGroupId);

        String profileId = getProfileId();
        String auxProfileId = getProfileIdAux();

        upsertOnTestRunMessage("profileId", profileId);
        upsertOnTestRunMessage("auxProfileId", auxProfileId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetGroupMembers() {
        try {
            String profileId = getTestRunMessageValue("profileId");
            String auxProfileId = getTestRunMessageValue("auxProfileId");

            List<Member> result = runFlowAndGetPayload("get-group-members");
            // Group should contain at least test account and auxiliary test account
            assertTrue(result.size() > 1);

            // Search for the main test account
            boolean found = false;
            for (Member member : result) {
                if (member.getId().equals(profileId)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);

            // Search for the auxiliary test account
            found = false;
            for (Member member : result) {
                if (member.getId().equals(auxProfileId)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }


}
