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
			testObjects = (HashMap<String, Object>) context.getBean("deleteObjectTestData");
			
			String profileId = getProfileId();
			testObjects.put("profileId", profileId);
			
			String albumId = publishAlbum((String) testObjects.get("albumName"), (String) testObjects.get("msg"), profileId);
			testObjects.put("albumId", albumId);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({ RegressionTests.class })
	@Test
	public void testDeleteObject() {
		try {
			String albumId = (String) testObjects.get("albumId");
			
			MessageProcessor flow = lookupFlowConstruct("delete-object");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			Album album = getAlbum(albumId);
			assertTrue(album == null);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

}