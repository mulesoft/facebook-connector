package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Group;

public class GetGroupPictureTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
    	initializeTestRunMessage("getGroupPictureTestData");
    	
		String query = (String) getTestRunMessageValue("q");
    	List<Group> groups = searchGroups(query);
		upsertOnTestRunMessage("group", groups.get(0).getId());
	}

	@Category({RegressionTests.class})
	@Test
	public void testGetGroupPicture() {
		try {
			byte[] result = runFlowAndGetPayload("get-group-picture");
			assertTrue(result.length > 0);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}


}
