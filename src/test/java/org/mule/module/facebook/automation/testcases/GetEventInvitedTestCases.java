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
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.*;

public class GetEventInvitedTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getEventInvitedTestData");

        String profileId = getProfileId();
        String auxProfileId = getProfileIdAux();

        String eventName = getTestRunMessageValue("eventName");
        String startTime = getTestRunMessageValue("startTime");

        String eventId = publishEvent(profileId, eventName, startTime);

        upsertOnTestRunMessage("eventId", eventId);
        upsertOnTestRunMessage("profileId", profileId);
        upsertOnTestRunMessage("auxProfileId", auxProfileId);

        inviteUser(eventId, auxProfileId);
    }

    @SuppressWarnings("unchecked")
    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testGetEventInvited() {
        try {
            String profileId = getTestRunMessageValue("profileId");
            String auxProfileId = getTestRunMessageValue("auxProfileId");

            List<User> users = runFlowAndGetPayload("get-event-invited");

            // There should be two users invited, the one we invited in the setUp method
            // and the one who created the event.
            assertTrue(users.size() == 2);

            User first = users.get(0);
            User second = users.get(1);

            // Assert that the two invited users do not share the same ID,
            // and that their IDs correspond to the profile IDs retrieved in the setUp method
            assertTrue(first.getId().equals(auxProfileId) || first.getId().equals(profileId));
            assertTrue(second.getId().equals(auxProfileId) || second.getId().equals(profileId));
            assertFalse(second.getId().equals(first.getId()));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String eventId = getTestRunMessageValue("eventId");
        deleteObject(eventId);
    }

}