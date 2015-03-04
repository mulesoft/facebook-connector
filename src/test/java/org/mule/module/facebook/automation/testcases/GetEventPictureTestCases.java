/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetEventPictureTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getEventPictureTestData");

        String profileId = getProfileId();
        String eventName = (String) getTestRunMessageValue("eventName");
        String startTime = (String) getTestRunMessageValue("startTime");

        String eventId = publishEvent(profileId, eventName, startTime);
        upsertOnTestRunMessage("eventId", eventId);
        upsertOnTestRunMessage("objectId", eventId);
    }

    @Category({RegressionTests.class})
    @Test
    public void testGetEventPicture() {
        try {
            byte[] picture = runFlowAndGetPayload("get-event-picture");
            assertTrue(picture.length > 0);
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