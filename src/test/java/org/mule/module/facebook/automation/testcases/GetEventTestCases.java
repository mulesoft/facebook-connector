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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GetEventTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getEventTestData");

        String profileId = getProfileId();
        String eventName = (String) getTestRunMessageValue("eventName");
        String startTime = (String) getTestRunMessageValue("startTime");

        String eventId = publishEvent(profileId, eventName, startTime);

        upsertOnTestRunMessage("eventId", eventId);
        upsertOnTestRunMessage("objectId", eventId);
    }

    @Category({RegressionTests.class})
    @Test
    public void testGetEvent() {
        try {
            String eventId = (String) getTestRunMessageValue("eventId");
            String eventName = (String) getTestRunMessageValue("eventName");

            Event event = getEvent((String) getTestRunMessageValue("eventId"));

            assertEquals(eventId, event.getId());
            assertEquals(eventName, event.getName());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String objectId = (String) getTestRunMessageValue("objectId");
        deleteObject(objectId);
    }

}