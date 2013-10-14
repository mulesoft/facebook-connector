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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Post;

public class GetUserHomeTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
    	testObjects = (HashMap<String,Object>) context.getBean("getUserHomeTestData");
		
    	String profileId = getProfileId();
    	testObjects.put("user", profileId);
    	
    	List<String> messages = (List<String>) testObjects.get("messages");
    	List<String> messageIds = new ArrayList<String>();
    	
    	for (String message : messages) {
			String messageId = publishMessage(profileId, message);
			messageIds.add(messageId);
		}
    	
    	testObjects.put("messageIds", messageIds);
	}
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserHome() {
		try {
			List<String> messageIds = (List<String>) testObjects.get("messageIds");
			
			MessageProcessor flow = lookupFlowConstruct("get-user-home");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			Collection<Post> posts = (Collection<Post>) response.getMessage().getPayload();
			assertTrue(posts.size() >= messageIds.size());
			
			for (String messageId : messageIds) {
				boolean found = false;
				for (Post post : posts) {
					if (post.getId().equals(messageId)) {
						found = true;
						break;
					}
				}
				assertTrue(found);
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
    
    @After
    public void tearDown() throws Exception {
    	List<String> messageIds = (List<String>) testObjects.get("messageIds");
    	for (String messageId : messageIds) {
			deleteObject(messageId);
		}
    }
    
}