/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Album;

public class GetUserAlbumsTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String, Object>) getBeanFromContext("getUserAlbumsTestData");
		
		String profileId = getProfileId();
		String albumId = publishAlbum((String) testObjects.get("albumName"), (String) testObjects.get("msg"), profileId);

		testObjects.put("profileId", profileId);
		testObjects.put("albumId", albumId);
	}

	@SuppressWarnings("unchecked")
	@Category({ RegressionTests.class })
	@Test
	public void testGetUserAlbums() {
		try {
			String profileId = (String) testObjects.get("profileId");
			final String albumId = (String) testObjects.get("albumId");
			String since = (String) testObjects.get("since");
			String until = (String) testObjects.get("until");
			String limit = (String) testObjects.get("limit");
			String offset = (String) testObjects.get("offset");
			
			Collection<Album> albums = requestUserAlbums(profileId,	since, until, limit, offset);
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