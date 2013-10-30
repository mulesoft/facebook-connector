package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.facebook.types.Member;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Group;

public class GetGroupMembersTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
    	initializeTestRunMessage("getGroupMembersTestData");

    	String expectedGroupId = getExpectedGroupId();
    	upsertOnTestRunMessage("group", expectedGroupId);

    	String profileId = getProfileId();
    	String auxProfileId = getProfileIdAux();
    	
    	upsertOnTestRunMessage("profileId", profileId);
    	upsertOnTestRunMessage("auxProfileId", auxProfileId);
	}

	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetGroupMembers() {
		try {
			String profileId = getTestRunMessageValue("profileId");
			String auxProfileId = getTestRunMessageValue("auxProfileId");
			
			List<Member> result = runFlowAndGetPayload("get-group-members");
			// Group should contain at least test account and auxiliary test account 
			assertTrue(result.size() > 1);
			
			// Search for the main test account
			boolean found = false;			
			for (Member member : result) {
				if (member.getId().equals(profileId)) {
					found = true;
					break;
				}
			}
			assertTrue(found);
			
			// Search for the auxiliary test account
			found = false;
			for (Member member : result) {
				if (member.getId().equals(auxProfileId)) {
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
