package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.User;

public class LoggedUserDetailsTestCases extends FacebookTestParent {
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testLoggedUserDetails() {
		try {
			User loggedIn = runFlowAndGetPayload("logged-user-details");
			assertNotNull(loggedIn);
			assertTrue(StringUtils.isNotBlank(loggedIn.getId()));
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
}
