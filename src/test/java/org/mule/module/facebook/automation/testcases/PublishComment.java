package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Page;

public class PublishComment extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
public void PublishComment() {
		
		
    	
    	
    	
    	testObjects = (HashMap<String,Object>) context.getBean("publishCommentTestData");
    	
    	
    	
    	
		MessageProcessor flow = lookupFlowConstruct("publish-comment");
    	
		try {
			String postedMessageID = publishMessage(testObjects.get("profileId").toString(), "TestMessage");
			
			testObjects.put("postId", postedMessageID);
			
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			String commentID = (String) response.getMessage().getPayload();
			
			System.out.println(commentID);
//			assertEquals("Facebook Developers", page.getName());

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
     
	}
}
