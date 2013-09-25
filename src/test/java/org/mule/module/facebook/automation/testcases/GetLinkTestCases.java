package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.facebook.FacebookConnectorUnitTest;
import org.mvel2.ast.AssertNode;

import com.restfb.types.Link;
import com.restfb.types.StatusMessage;

public class GetLinkTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp(){
		testObjects = (HashMap<String, Object>) context
				.getBean("publishLinkTestData");
		String profileId;
		
		
		try {
			profileId = getProfileId();
			String msg = testObjects.get("msg").toString();
			String link = testObjects.get("link").toString();
			testObjects.put("profileId", profileId);
			String messageId = publishMessage(profileId, msg , link);
			testObjects.put("messageId", messageId);
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
		

	}

	
	
	@Test
	public void testGetLink(){
		MessageProcessor flow = lookupFlowConstruct("get-link");
    	MuleEvent response;
		Link myLink = null;
    	
		try {
			response = flow.process(getTestEvent(testObjects));
			myLink = (Link) response.getMessage().getPayload();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
		assertNotNull(myLink);
		assertEquals(testObjects.get("link").toString(), myLink.getLink());
		assertEquals(testObjects.get("msg"), myLink.getMessage());
		
	}
	
	
	@After
	public void tearDown() {
		String messageId = testObjects.get("messageId").toString();
		try {
			deleteObject(messageId);
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

}
