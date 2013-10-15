package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Group;

public class GetGroupTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
    	initializeTestRunMessage("getGroupTestData");
		String query = (String) getTestRunMessageValue("q");
    	List<Group> groups = searchGroups(query);
    	//get first group in the list this will be used to compare with
    	upsertOnTestRunMessage("groupGotBySearch",groups.get(0));
	}

	@Category({RegressionTests.class})
	@Test
	public void testGetGroup(){
		try {
			Group groupGotBySearch = (Group) getTestRunMessageValue("groupGotBySearch");
			upsertOnTestRunMessage("group", groupGotBySearch.getId());
			
			Group group = runFlowAndGetPayload("get-group");

			assertEquals(groupGotBySearch.getId(), group.getId());
			assertEquals(groupGotBySearch.getName(), group.getName());
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}


}
