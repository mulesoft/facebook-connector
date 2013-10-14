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
import org.mule.module.facebook.types.Member;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Group;

public class GetGroupMembersTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
    	testObjects = (HashMap<String,Object>) getBeanFromContext("getGroupMembersTestData");
		String query = (String) testObjects.get("q");
    	List<Group> groups = searchGroups(query);
		testObjects.put("group", groups.get(0).getId());
	}

	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetGroupMembers() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-group-members");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			List<Member> result = (List<Member>) response.getMessage().getPayload();
			
			assertTrue(result.size() > 0);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}


}
