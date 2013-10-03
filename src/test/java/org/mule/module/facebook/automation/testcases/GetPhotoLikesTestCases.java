/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Post.Likes;

public class GetPhotoLikesTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		try {
			testObjects = (HashMap<String,Object>) context.getBean("getPhotoLikesTestData");
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetPhotoLikes() {
    	
		MessageProcessor flow = lookupFlowConstruct("get-photo-likes");
    	
		try {
			MuleEvent response = flow.process(getTestEvent(testObjects));
			Likes result = (Likes) response.getMessage().getPayload();
			
			assertNotNull(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
     
	}
    
    
}