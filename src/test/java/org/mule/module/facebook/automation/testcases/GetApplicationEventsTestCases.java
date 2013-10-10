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

import com.restfb.types.Event;

public class GetApplicationEventsTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) context.getBean("getApplicationEventsTestData");
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetApplicationEvents() {
		try {
			MessageProcessor flow = lookupFlowConstruct("get-application-events");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<Event> result = (List<Event>) response.getMessage().getPayload();
			
			assertNotNull(result);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
