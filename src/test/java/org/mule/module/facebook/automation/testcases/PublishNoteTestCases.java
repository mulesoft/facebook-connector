package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

public class PublishNoteTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testPublishNote() {
		testObjects = (HashMap<String, Object>) context
				.getBean("publishNoteTestData");

		MessageProcessor flow = lookupFlowConstruct("publish-note");


		try {
			// TODO: get object back to check this is correct.		
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
		
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
