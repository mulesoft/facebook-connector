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

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Album;

public class GetPageAlbumsTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String,Object>) context.getBean("getPageAlbumsTestData");

		String page = (String) testObjects.get("page");
		String albumName = (String) testObjects.get("albumName");
		String msg = (String) testObjects.get("msg");
		
		String albumId = publishAlbumOnPage(albumName, msg, page);
		testObjects.put("albumId", albumId);
	}
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetPageAlbums() {
		try {
			String albumId = (String) testObjects.get("albumId");
			
			MessageProcessor flow = lookupFlowConstruct("get-page-albums");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			List<Album> albums = (List<Album>) response.getMessage().getPayload();
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