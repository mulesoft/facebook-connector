/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Album;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetPageAlbumsTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getPageAlbumsTestData");

        String page = (String) getTestRunMessageValue("page");
        String albumName = (String) getTestRunMessageValue("albumName");
        String msg = (String) getTestRunMessageValue("msg");

        String albumId = publishAlbumOnPage(albumName, msg, page);
        upsertOnTestRunMessage("albumId", albumId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetPageAlbums() {
        try {
            String albumId = (String) getTestRunMessageValue("albumId");

            List<Album> albums = runFlowAndGetPayload("get-page-albums");
            assertTrue(albums.size() > 0);

            boolean found = false;
            for (Album album : albums) {
                if (album.getId().equals(albumId)) {
                    found = true;
                    break;
                }
            }

            assertTrue(found);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}