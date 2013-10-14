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

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Comment;

public class GetPhotoCommentsTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String,Object>) context.getBean("getPhotoCommentsTestData");
		String profileId = getProfileId();
		testObjects.put("user", profileId);
		
		String caption = (String) testObjects.get("caption");
		File photoFile = new File(getClass().getClassLoader().getResource((String) testObjects.get("photoFilePath")).toURI());
		
		String photoId = publishPhoto(profileId, caption, photoFile);
		testObjects.put("photo", photoId);
		
		String commentId = publishComment((String) testObjects.get("photo"), (String) testObjects.get("msg"));
		testObjects.put("commentId", commentId);
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetPhotoComments() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-photo-comments");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			List<Comment> result = (List<Comment>) response.getMessage().getPayload();
			
			assertTrue(result.size() == 1);
			Comment comment = result.get(0);
			assertTrue(comment.getId().equals((String) testObjects.get("commentId")));
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
    
	@After
	public void tearDown() throws Exception {
		String commentId = (String) testObjects.get("commentId");
		deleteObject(commentId);
		String photoId = (String) testObjects.get("photo");
		deleteObject(photoId);
	}
    
}