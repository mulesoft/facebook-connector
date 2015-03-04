/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class SearchCheckinsTestCases extends FacebookTestParent {

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("searchCheckinsTestData");
        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);

        // Check-in at Pizza place
        String messageId = publishMessage(profileId, "I like pizza", null, null, null, null, null, "132738745815");
        upsertOnTestRunMessage("messageId", messageId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Ignore("Facebook API bug. See https://developers.facebook.com/bugs/536595293095881")
    @Test
    public void testSearchCheckins() {
        try {
            Collection<String> searchResponse = runFlowAndGetPayload("search-checkins");

            assertFalse(searchResponse.isEmpty());
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String messageId = (String) getTestRunMessageValue("messageId");
        deleteObject(messageId);
    }

}