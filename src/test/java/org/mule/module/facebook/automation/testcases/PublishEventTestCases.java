/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PublishEventTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("publishEventTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);
    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testPublishEvent() {
        try {
            String profileId = (String) getTestRunMessageValue("profileId");
            String eventName = (String) getTestRunMessageValue("eventName");
            String startTime = (String) getTestRunMessageValue("startTime");

            String eventId = publishEvent(profileId, eventName, startTime);
            upsertOnTestRunMessage("objectId", eventId);

            assertTrue(StringUtils.isNotEmpty(eventId));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        deleteObject((String) getTestRunMessageValue("objectId"));
    }
}
