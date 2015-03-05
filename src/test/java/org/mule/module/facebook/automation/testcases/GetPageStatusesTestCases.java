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

import java.util.List;

import static org.junit.Assert.*;

public class GetPageStatusesTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getPageStatusesTestData");

        String page = (String) getTestRunMessageValue("page");
        String msg = (String) getTestRunMessageValue("msg");

        String messageId = publishMessage(page, msg);
        upsertOnTestRunMessage("messageId", messageId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetPageStatuses() {
        try {
            String pageId = (String) getTestRunMessageValue("page");
            String messageId = (String) getTestRunMessageValue("messageId");

            List<StatusMessage> result = runFlowAndGetPayload("get-page-statuses");
            assertTrue(result.size() == 1);

            StatusMessage message = result.get(0);
            assertEquals(pageId + "_" + message.getId(), messageId);
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