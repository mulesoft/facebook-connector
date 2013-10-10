package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Group;
import com.restfb.types.Post;

public class GetGroupWallTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp(){
		try {
	    	testObjects = (HashMap<String,Object>) context.getBean("getGroupWallTestData");
			String query = (String) testObjects.get("q");
	    	List<Group> groups = searchGroups(query);
			testObjects.put("group", groups.get(0).getId());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetGroupWall(){
		try {
			MessageProcessor flow = lookupFlowConstruct("get-group-wall");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			List<Post> result = (List<Post>) response.getMessage().getPayload();
			
			assertNotNull(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}


}
