package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Comment;
import com.restfb.types.Group;

public class GetGroupTestCase extends FacebookTestParent {
	
	@Before
	public void tearUp(){
		try {
	    	testObjects = (HashMap<String,Object>) context.getBean("getGroupTestData");
			String query = testObjects.get("q").toString();
	    	List<Group> groups = searchGroups(query);
	    	//get first group in the list this will be used to compare with
	    	testObjects.put("groupGotBySearch",groups.get(0));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	
	@Test
	public void getGroupTestCase(){
		try {
			Group groupGotBySearch = (Group) testObjects.get("groupGotBySearch");
			testObjects.put("group", groupGotBySearch.getId());
			
			
			MessageProcessor flow = lookupFlowConstruct("get-group");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			Group group = (Group) response.getMessage().getPayload();

			assertNotNull(group.getId());
			assertNotNull(group.getName());
			assertEquals(groupGotBySearch.getName(), group.getName());

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}


}
