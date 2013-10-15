package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Page;

public class SearchPagesTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("searchPagesTestData");
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testSearchPages() {
		try {
			List<Page> result = runFlowAndGetPayload("search-pages");
			assertTrue(result.size() > 0);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}

}