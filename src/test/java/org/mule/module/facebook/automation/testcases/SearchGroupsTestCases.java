package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertNotNull;
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

public class SearchGroupsTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String, Object>) context.getBean("searchGroupTestData");
	}

	@Category({RegressionTests.class})
	@Test
	public void testSearchGroup() {
		try {
			MessageProcessor flow = lookupFlowConstruct("search-groups");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			List<Group> groups = (List<Group>) response.getMessage().getPayload();

			for (Group group: groups){
				assertNotNull(group.getId());
			}
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}

	}

}
