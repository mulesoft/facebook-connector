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

public class GetEventAttendingTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getEventAttendingTestData");

        String profileId = getProfileId();

        String auxProfileId = getProfileIdAux();

        String eventName = getTestRunMessageValue("eventName");
        String startTime = getTestRunMessageValue("startTime");

        String eventId = publishEventAux(auxProfileId, eventName, startTime);
        attendEvent(eventId);

        upsertOnTestRunMessage("profileId", profileId);
        upsertOnTestRunMessage("auxProfileId", auxProfileId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetEventAttending() {
        try {
            String profileId = getTestRunMessageValue("profileId");
            String auxProfileId = getTestRunMessageValue("auxProfileId");

            // Get list of users attending the event
            List<User> users = runFlowAndGetPayload("get-event-attending");
            // There should be two.. the creator and the attendee
            assertTrue(users.size() == 2);

            User first = users.get(0);
            User second = users.get(1);

            assertTrue(first.getId().equals(profileId) || first.getId().equals(auxProfileId));
            assertTrue(second.getId().equals(profileId) || second.getId().equals(auxProfileId));
            assertFalse(first.getId().equals(second.getId()));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String eventId = getTestRunMessageValue("eventId");
        deleteObjectAux(eventId);
    }

}