/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.tests.ConnectorTestUtils;

public class DeleteObjectTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String, Object>) context.getBean("deleteObjectTestData");
			
		String profileId = getProfileId();
		testObjects.put("profileId", profileId);
			
		String msg = (String) testObjects.get("msg");
			
		String msgId = publishMessage(profileId, msg);
		testObjects.put("objectId", msgId);
	}

	@Category({ SmokeTests.class, RegressionTests.class })
	@Test
	public void testDeleteObject() {
		try {
			MessageProcessor flow = lookupFlowConstruct("delete-object");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			Boolean result = (Boolean) response.getMessage().getPayload();
			assertTrue(result);
		} catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}

	}

}