package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.User;

public class SearchUsersTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) context.getBean("searchUsersTestData");
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testSearchUsers() {
		try {
			MessageProcessor flow = lookupFlowConstruct("search-users");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<User> users = (List<User>) response.getMessage().getPayload();
			assertTrue(users.size() > 0);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {

	}
	
}
