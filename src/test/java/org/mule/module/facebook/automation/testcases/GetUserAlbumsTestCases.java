/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.restfb.types.Album;

public class GetUserAlbumsTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context
					.getBean("getUserAlbumsTestData");
			publishAlbum((String) testObjects.get("albumName"),
					(String) testObjects.get("msg"),
					(String) testObjects.get("profileId"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({ RegressionTests.class })
	@Test
	public void testGetUserAlbums() {

		try {
			Collection<Album> albums = requestUserAlbums(
					(String) testObjects.get("user"),
					(String) testObjects.get("since"),
					(String) testObjects.get("until"),
					(String) testObjects.get("limit"),
					(String) testObjects.get("offset"));

			Album firstAlbum = (Album) albums.toArray()[0];

			assertEquals(testObjects.get("albumName"), firstAlbum.getName());

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void tearDown() {
		try {
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}