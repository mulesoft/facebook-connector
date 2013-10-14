package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.facebook.types.GetApplicationTaggedResponseType;
import org.mule.modules.tests.ConnectorTestUtils;

public class GetApplicationTaggedTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) context.getBean("getApplicationTaggedTestData");
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetApplicationTagged() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-application-tagged");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<GetApplicationTaggedResponseType> result = (List<GetApplicationTaggedResponseType>) response.getMessage().getPayload();
			
			assertNotNull(result);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
}
