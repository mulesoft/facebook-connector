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
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;

import com.restfb.types.Note;

public class GetPageNotesTestCases extends FacebookTestParent {
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (HashMap<String,Object>) context.getBean("getPageNotesTestData");
		
		String page = (String) testObjects.get("page");
		String msg = (String) testObjects.get("msg");
		String subject = (String) testObjects.get("subject");
		
		String noteId = publishNoteOnPage(page, msg, subject);
		testObjects.put("noteId", noteId);
	}
	
    @SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetPageNotes() {
		try {
			String noteId = (String) testObjects.get("noteId");
			
			MessageProcessor flow = lookupFlowConstruct("get-page-notes");
			MuleEvent response = flow.process(getTestEvent(testObjects));

			List<Note> result = (List<Note>) response.getMessage().getPayload();
			assertTrue(result.size() == 1);
			
			Note note = result.get(0);
			assertTrue(note.getId().equals(noteId));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
    
    @After
    public void tearDown() throws Exception {
    	String noteId = (String) testObjects.get("noteId");
    	deletePageObject(noteId);
    }
    
}