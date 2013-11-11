package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Post;

public class GetGroupWallTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
    	initializeTestRunMessage("getGroupWallTestData");
    	
    	String groupId = getExpectedGroupId();
    	upsertOnTestRunMessage("group", groupId);
    	
    	List<String> messages = getTestRunMessageValue("messages");
    	List<String> messageIds = new ArrayList<String>();
    	
    	for (String msg : messages) {
    		String messageId = publishMessage(groupId, msg);
    		messageIds.add(messageId);
    		upsertOnTestRunMessage("messageIds", messageIds);
		}
	}

	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetGroupWall(){
		try {
			List<String> messageIds = getTestRunMessageValue("messageIds");
			List<Post> result = runFlowAndGetPayload("get-group-wall");
			assertEquals(messageIds.size(), result.size());
			
			for (Post post : result) {
				assertTrue(messageIds.contains(post.getId()));
			}
			
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}

	@After
	public void tearDown() throws Exception {
		List<String> messageIds = getTestRunMessageValue("messageIds");
		for (String messageId : messageIds) {
			deleteObject(messageId);
		}
	}

}
