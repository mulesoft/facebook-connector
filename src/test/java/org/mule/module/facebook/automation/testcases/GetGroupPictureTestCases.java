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

import com.restfb.types.Group;

public class GetGroupPictureTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp(){
		try {
	    	testObjects = (HashMap<String,Object>) context.getBean("getGroupPictureTestData");
			String query = (String) testObjects.get("q");
	    	List<Group> groups = searchGroups(query);
			testObjects.put("group", groups.get(0).getId());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Category({RegressionTests.class})
	@Test
	public void testGetGroupPicture(){
		try {
			MessageProcessor flow = lookupFlowConstruct("get-group-picture");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			byte[] result = (byte[]) response.getMessage().getPayload();
			
			assertTrue(result.length > 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}


}