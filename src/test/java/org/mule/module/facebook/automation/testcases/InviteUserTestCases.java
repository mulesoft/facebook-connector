/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class InviteUserTestCases extends FacebookTestParent {

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("inviteUserTestData");

        String profileId = getProfileId();
        String auxProfileId = getProfileIdAux();
        upsertOnTestRunMessage("profileId", profileId);
        upsertOnTestRunMessage("auxProfileId", auxProfileId);

        String eventName = getTestRunMessageValue("eventName");
        String startTime = getTestRunMessageValue("startTime");

        String eventId = publishEvent(profileId, eventName, startTime);
        upsertOnTestRunMessage("eventId", eventId);

    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testInviteUser() {
        try {
            String auxProfileId = getTestRunMessageValue("auxProfileId");
            String eventId = getTestRunMessageValue("eventId");
            String until = getTestRunMessageValue("until");
            String since = getTestRunMessageValue("since");
            String offset = getTestRunMessageValue("offset");
            String limit = getTestRunMessageValue("limit");

            Boolean result = inviteUser(eventId, auxProfileId);
            assertTrue(result);

            List<User> invited = getEventInvited(eventId, until, since, limit, offset);

            boolean found = false;
            for (User user : invited) {
                if (user.getId().equals(auxProfileId)) {
                    found = true;
                    break;
                }
            }

            assertTrue(found);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @After
    public void tearDown() throws Exception {
        String eventId = getTestRunMessageValue("eventId");
        deleteObject(eventId);
    }

}
