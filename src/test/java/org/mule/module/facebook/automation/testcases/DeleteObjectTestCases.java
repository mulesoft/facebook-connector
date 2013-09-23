/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Album;

public class DeleteObjectTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String, Object>) context
					.getBean("deleteObjectTestData");
			publishAlbum((String) testObjects.get("albumName"),
					(String) testObjects.get("msg"),
					(String) testObjects.get("profileId"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({ RegressionTests.class })
	@Test
	public void testDeleteObject() {

		testObjects = (HashMap<String, Object>) context
				.getBean("deleteObjectTestData");

		try {
			Collection<Album> albums = requestUserAlbums((String) testObjects.get("user"),
					(String) testObjects.get("since"),
					(String) testObjects.get("until"),
					(String) testObjects.get("limit"),
					(String) testObjects.get("offset"));

			Album album = (Album) albums.toArray()[0];
			
			String albumId = album.getId();
			
			testObjects.put("objectId", albumId);
			
			MessageProcessor flow = lookupFlowConstruct("delete-object");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Object res = (Object) response.getMessage().getPayload();
			
			// get back a HashMap: {albumName=albumForTest,
			// profileId=100006563414301, msg=msgForTest}
			// i.e. void return type
			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

}