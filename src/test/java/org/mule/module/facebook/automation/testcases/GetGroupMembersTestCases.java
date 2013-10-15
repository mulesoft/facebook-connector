package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.facebook.types.Member;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Group;

public class GetGroupMembersTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
    	initializeTestRunMessage("getGroupMembersTestData");
    	
		String query = (String) getTestRunMessageValue("q");
    	List<Group> groups = searchGroups(query);
		upsertOnTestRunMessage("group", groups.get(0).getId());
	}

	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetGroupMembers() {
		try {
			List<Member> result = runFlowAndGetPayload("get-group-members");
			assertTrue(result.size() > 0);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}


}
