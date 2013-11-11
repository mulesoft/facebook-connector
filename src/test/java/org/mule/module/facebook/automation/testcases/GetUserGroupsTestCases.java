/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Group;

public class GetUserGroupsTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
    	initializeTestRunMessage("getUserGroupsTestData");
			
    	String profileId = getProfileId();
    	upsertOnTestRunMessage("user", profileId);
    	
    	String groupId = getExpectedGroupId();
    	upsertOnTestRunMessage("groupId", groupId);
	}
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserGroups() {
		try {
			String groupId = getTestRunMessageValue("groupId");
						
			List<Group> result = runFlowAndGetPayload("get-user-groups");
			assertFalse(result.isEmpty());
			
			boolean found = false;
			for (Group group : result) {
				if (group.getId().equals(groupId)) {
					found = true;
					break;
				}
			}
			assertTrue(found);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
    
}