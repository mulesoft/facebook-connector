/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetUserEventsTestCases extends FacebookTestParent {


    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getUserEventsTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("user", profileId);

        String eventId = publishEvent(profileId, (String) getTestRunMessageValue("eventName"), (String) getTestRunMessageValue("startTime"));
        upsertOnTestRunMessage("objectId", eventId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetUserEvents() {
        try {
            List<Event> result = runFlowAndGetPayload("get-user-events");

            assertTrue(result.size() != 0);

            String eventId = (String) getTestRunMessageValue("objectId");
            boolean containsPublishedEvent = false;
            for (Event event : result) {
                if (eventId.equals(event.getId())) {
                    containsPublishedEvent = true;
                    break;
                }
            }

            assertTrue(containsPublishedEvent);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

    @After
    public void tearDown() throws Exception {
        deleteObject((String) getTestRunMessageValue("objectId"));
    }

}