/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Post;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GetEventWallTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getEventWallTestData");

        String profileId = getProfileId();

        String eventName = getTestRunMessageValue("eventName").toString();
        String startTime = getTestRunMessageValue("startTime").toString();
        String eventId = publishEvent(profileId, eventName, startTime);
        upsertOnTestRunMessage("eventId", eventId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetEventWall() {
        try {
            List<Post> wall = runFlowAndGetPayload("get-event-wall");
            assertEquals(wall.size(), 1);

            List<String> messages = getTestRunMessageValue("messages");
            List<String> messageIds = new ArrayList<String>();
            messageIds.add(wall.get(0).getId());

            for (String message : messages) {
                String messageId = publishMessage((String) getTestRunMessageValue("eventId"), message);
                messageIds.add(messageId);
            }
            wall = runFlowAndGetPayload("get-event-wall");

            assertEquals(wall.size(), messageIds.size());
            for (Post post : wall) {
                assertTrue(messageIds.contains(post.getId()));
            }
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
