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

public class GetEventNoReplyTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getEventNoReplyTestData");

        String profileId = getProfileId();
        String auxProfileId = getProfileIdAux();

        upsertOnTestRunMessage("profileId", profileId);
        upsertOnTestRunMessage("auxProfileId", auxProfileId);

        String eventName = getTestRunMessageValue("eventName");
        String startTime = getTestRunMessageValue("startTime");

        String eventId = publishEvent(profileId, eventName, startTime);
        upsertOnTestRunMessage("eventId", eventId);

        inviteUser(eventId, auxProfileId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetEventNoReply() {
        try {
            String auxProfileId = getTestRunMessageValue("auxProfileId");

            List<User> users = runFlowAndGetPayload("get-event-no-reply");
            assertTrue(users.size() == 1);

            User user = users.get(0);
            assertEquals(user.getId(), auxProfileId);
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