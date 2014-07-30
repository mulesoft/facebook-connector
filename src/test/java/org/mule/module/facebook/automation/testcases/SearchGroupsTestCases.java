/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

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
