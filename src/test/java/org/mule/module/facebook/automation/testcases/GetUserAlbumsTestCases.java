/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Album;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetUserAlbumsTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getUserAlbumsTestData");

        String profileId = getProfileId();
        String albumId = publishAlbum((String) getTestRunMessageValue("albumName"), (String) getTestRunMessageValue("msg"), profileId);

        upsertOnTestRunMessage("profileId", profileId);
        upsertOnTestRunMessage("albumId", albumId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetUserAlbums() {
        try {
            String profileId = (String) getTestRunMessageValue("profileId");
            final String albumId = (String) getTestRunMessageValue("albumId");
            String since = (String) getTestRunMessageValue("since");
            String until = (String) getTestRunMessageValue("until");
            String limit = (String) getTestRunMessageValue("limit");
            String offset = (String) getTestRunMessageValue("offset");

            Collection<Album> albums = getUserAlbums(profileId, since, until, limit, offset);
            assertTrue(albums.size() > 0);

            Collection<Album> matching = CollectionUtils.select(albums, new Predicate() {

                @Override
                public boolean evaluate(Object object) {
                    Album album = (Album) object;
                    return album.getId().equals(albumId);
                }
            });

            assertTrue(matching.size() == 1);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {

    }
}