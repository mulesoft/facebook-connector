/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Link;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PublishLinkTestCases extends FacebookTestParent {

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("publishLinkTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);
    }

    @SuppressWarnings("unchecked")
    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testPublishLink() {
        try {
            String messageId = runFlowAndGetPayload("publish-link");

            upsertOnTestRunMessage("messageId", messageId);
            Link resultLink = getLink(messageId);

            assertTrue(messageId.equals(resultLink.getId()));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String profileId = (String) getTestRunMessageValue("profileId");
        String messageId = (String) getTestRunMessageValue("messageId");
        deleteObject(profileId + "_" + messageId);
    }
}
