/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Group;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GetGroupTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getGroupTestData");

        String expectedGroupId = getExpectedGroupId();
        upsertOnTestRunMessage("group", expectedGroupId);
    }

    @Category({RegressionTests.class})
    @Test
    public void testGetGroup() {
        try {
            String groupId = getTestRunMessageValue("group");
            Group group = runFlowAndGetPayload("get-group");
            assertEquals(group.getId(), groupId);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }


}
