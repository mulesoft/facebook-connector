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
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Comment;

public class GetAlbumCommentsTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String,Object>) context.getBean("getAlbumCommentsTestData");

		String albumName = (String) testObjects.get("albumName");
		String msg = (String) testObjects.get("msg");
		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
			
		String albumId = publishAlbum(albumName, msg, profileId);
		testObjects.put("albumId", albumId);
			
		String commentMsg = (String) testObjects.get("commentMsg");
		String commentId = publishComment(albumId, commentMsg);
		testObjects.put("commentId", commentId);
	}
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetAlbumComments() {
		try {
			String albumId = (String) testObjects.get("albumId");
			testObjects.put("album", albumId);
			final String commentId = (String) testObjects.get("commentId");
			
			MessageProcessor flow = lookupFlowConstruct("get-album-comments");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			Collection<Comment> comments = (Collection<Comment>) response.getMessage().getPayload();
			
			Collection<Comment> matching = CollectionUtils.select(comments, new Predicate() {
				
				@Override
				public boolean evaluate(Object object) {
					Comment comment = (Comment) object;
					return comment.getId().equals(commentId);
				}
			});
			
			assertTrue(matching.size() == 1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
     
	}
    
}