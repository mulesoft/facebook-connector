package org.mule.module.facebook.automation.testcases;

import com.restfb.types.NamedFacebookType;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class GetUserTaggableFriendsTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getUserTaggableFriendsTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("user", profileId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetUserTaggableFriends() {
        try {

            List<NamedFacebookType> result = runFlowAndGetPayload("get-user-taggable-friends");
            assertNotNull(result);

        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
