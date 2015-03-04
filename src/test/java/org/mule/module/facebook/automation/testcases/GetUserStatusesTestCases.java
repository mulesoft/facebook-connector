/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.StatusMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GetUserStatusesTestCases extends FacebookTestParent {

    private List<StatusMessage> originalStatuses;

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getUserStatusesTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("user", profileId);
        originalStatuses = runFlowAndGetPayload("get-user-statuses");

        List<String> messages = (List<String>) getTestRunMessageValue("messages");
        List<String> messageIds = new ArrayList<String>();
        for (String message : messages) {
            String messageId = publishMessage(profileId, message);
            messageIds.add(messageId);
        }
        upsertOnTestRunMessage("messageIds", messageIds);
    }

    @Category({RegressionTests.class})
    @Test
    public void testGetUserStatuses() {
        try {
            String profileId = (String) getTestRunMessageValue("profileId");
            upsertOnTestRunMessage("user", profileId);

            List<String> postedStatuses = (List<String>) getTestRunMessageValue("messageIds");

            List<StatusMessage> newStatuses = runFlowAndGetPayload("get-user-statuses");

            assertEquals(originalStatuses.size() + postedStatuses.size(), newStatuses.size());
            for (StatusMessage status : originalStatuses) {
                if (postedStatuses.contains(status.getId())) {
                    // MessageId is of the form {userId}_{messageId}
                    // StatusId is of the form {messageId}
                    // So we assert by concatenating profileId (our userId) with statusId
                    assertTrue(postedStatuses.contains(profileId + "_" + status.getId()));
                }
            }
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        List<String> messageIds = (List<String>) getTestRunMessageValue("messageIds");
        for (String messageId : messageIds) {
            deleteObject(messageId);
        }
    }

}
