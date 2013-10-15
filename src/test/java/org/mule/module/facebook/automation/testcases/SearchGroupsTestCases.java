package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Group;

public class SearchGroupsTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("searchGroupTestData");
	}

	@Category({RegressionTests.class})
	@Test
	public void testSearchGroup() {
		try {
			List<Group> groups = runFlowAndGetPayload("search-groups");
			for (Group group: groups){
				assertNotNull(group.getId());
			}
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}

	}

}
