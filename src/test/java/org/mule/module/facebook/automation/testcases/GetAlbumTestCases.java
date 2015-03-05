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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GetAlbumTestCases extends FacebookTestParent {

    String albumId;
    String albumName;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getAlbumTestData");
        List<Album> albums = runFlowAndGetPayload("get-application-albums");
        if (albums.isEmpty()) {
            String profileId = getProfileId();
            upsertOnTestRunMessage("profileId", profileId);
            albumName = getTestRunMessageValue("albumName").toString();
            albumId = publishAlbum(albumName, (String) getTestRunMessageValue("msg"), (String) getTestRunMessageValue("profileId"));
        } else {
            Album album = albums.get(0);
            albumId = album.getId();
            albumName = album.getName();
            upsertOnTestRunMessage("albumName", albumName);
        }
        upsertOnTestRunMessage("album", albumId);
    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testGetAlbum() {
        try {
            Album album = runFlowAndGetPayload("get-album");
            assertEquals(album.getId(), albumId);
            assertEquals(album.getName(), albumName);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

}