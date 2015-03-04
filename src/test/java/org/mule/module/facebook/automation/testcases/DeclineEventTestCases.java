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

public class DeclineEventTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("declineEventTestData");

        String auxProfileId = getProfileIdAux();
        String eventName = getTestRunMessageValue("eventName");
        String startTime = getTestRunMessageValue("startTime");

        String eventId = publishEventAux(auxProfileId, eventName, startTime);
        upsertOnTestRunMessage("eventId", eventId);

        attendEvent(eventId);
    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testDeclineEvent() {
        try {
            String eventId = getTestRunMessageValue("eventId");
            Boolean result = declineEvent(eventId);
            assertTrue(result);
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