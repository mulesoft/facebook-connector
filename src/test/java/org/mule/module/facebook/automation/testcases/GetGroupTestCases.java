package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Group;

public class GetGroupTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
    	testObjects = (HashMap<String,Object>) context.getBean("getGroupTestData");
		String query = (String) testObjects.get("q");
    	List<Group> groups = searchGroups(query);
    	//get first group in the list this will be used to compare with
    	testObjects.put("groupGotBySearch",groups.get(0));
	}

	@Category({RegressionTests.class})
	@Test
	public void testGetGroup(){
		try {
			Group groupGotBySearch = (Group) testObjects.get("groupGotBySearch");
			testObjects.put("group", groupGotBySearch.getId());
			
			MessageProcessor flow = lookupFlowConstruct("get-group");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			Group group = (Group) response.getMessage().getPayload();

			assertEquals(groupGotBySearch.getId(), group.getId());
			assertEquals(groupGotBySearch.getName(), group.getName());
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}


}
