/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SetPagePictureFromLinkTestCases extends FacebookTestParent {

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("setPagePictureFromLinkTestData");
    }

    @Category({RegressionTests.class})
    @Test
    public void testSetPagePictureFromLink() {
        try {
            Boolean result = runFlowAndGetPayload("set-page-picture-from-link");
            assertTrue(result);
        } catch (Exception e) {
            fail("Test failed. Please make sure that you have less than 1000 photos in your page's Profile Picture album.\n" + ConnectorTestUtils.getStackTrace(e));
        }
    }

}
