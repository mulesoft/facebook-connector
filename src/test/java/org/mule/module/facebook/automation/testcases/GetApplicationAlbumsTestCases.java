/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Album;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class GetApplicationAlbumsTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetApplicationAlbums() {
        try {
            initializeTestRunMessage("getApplicationAlbumsTestData");

            List<Album> albums = runFlowAndGetPayload("get-application-albums");
            assertNotNull(albums);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

}