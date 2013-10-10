package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Page;

public class SearchPagesTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String, Object>) context.getBean("searchPagesTestData");
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testSearchPages() {
		try {
			MessageProcessor flow = lookupFlowConstruct("search-pages");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			List<Page> result = (List<Page>)  response.getMessage().getPayload();

			assertTrue(result.size() > 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}