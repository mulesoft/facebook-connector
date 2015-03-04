/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Note;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetPageNotesTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getPageNotesTestData");

        String page = (String) getTestRunMessageValue("page");
        String msg = (String) getTestRunMessageValue("msg");
        String subject = (String) getTestRunMessageValue("subject");

        String noteId = publishNoteOnPage(page, msg, subject);
        upsertOnTestRunMessage("noteId", noteId);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetPageNotes() {
        try {
            String noteId = (String) getTestRunMessageValue("noteId");

            List<Note> result = runFlowAndGetPayload("get-page-notes");
            assertTrue(result.size() == 1);

            Note note = result.get(0);
            assertTrue(note.getId().equals(noteId));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String noteId = (String) getTestRunMessageValue("noteId");
        deletePageObject(noteId);
    }

}