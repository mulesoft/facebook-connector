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
    	
    	String expectedGroupId = getExpectedGroupId();
    	upsertOnTestRunMessage("group", expectedGroupId);
	}

	@Category({RegressionTests.class})
	@Test
	public void testGetGroup(){
		try {
			String groupId = getTestRunMessageValue("group");
			Group group = runFlowAndGetPayload("get-group");
			assertEquals(group.getId(), groupId);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}


}
