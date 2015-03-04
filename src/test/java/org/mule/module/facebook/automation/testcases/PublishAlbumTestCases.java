/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Album;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PublishAlbumTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("publishAlbumTestData");
        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);
    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testPublishAlbum() {
        try {
            String albumName = (String) getTestRunMessageValue("albumName");
            String msg = (String) getTestRunMessageValue("msg");
            String profileId = (String) getTestRunMessageValue("profileId");

            String albumId = publishAlbum(albumName, msg, profileId);
            upsertOnTestRunMessage("albumId", albumId);

            Album retrievedAlbum = getAlbum(albumId);
            assertEquals(retrievedAlbum.getId(), albumId);
            assertEquals(retrievedAlbum.getName(), albumName);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String albumId = (String) getTestRunMessageValue("albumId");

        // 	Deletion of albums cannot be done by the Facebook API.
        //	deleteObject(albumId);
    }

}