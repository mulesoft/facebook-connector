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

import static org.junit.Assert.*;

public class GetUserNotesTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("publishNoteTestData");
        String msg = getTestRunMessageValue("msg").toString();
        String subject = getTestRunMessageValue("subject").toString();

        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);
        String noteid = publishNote(profileId, msg, subject);
        upsertOnTestRunMessage("noteid", noteid);
    }

    @SuppressWarnings("unchecked")
    @Category({RegressionTests.class})
    @Test
    public void testGetUserNotes() {
        try {
            String noteId = getTestRunMessageValue("noteid").toString();

            List<Note> notes = runFlowAndGetPayload("get-user-notes");

            Boolean found = false;

            for (Note note : notes) {
                if (note.getId().equals(noteId)) {
                    found = true;
                    assertTrue(note.getMessage().contains(getTestRunMessageValue("msg").toString()));
                    assertEquals(note.getSubject(), getTestRunMessageValue("subject")
                            .toString());
                }

            }
            assertTrue(found);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }

    }

    @After
    public void tearDown() throws Exception {
        String noteId = getTestRunMessageValue("noteid").toString();
        deleteObject(noteId);
    }
}
