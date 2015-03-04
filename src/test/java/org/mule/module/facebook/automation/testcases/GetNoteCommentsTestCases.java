/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Comment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.*;

public class GetNoteCommentsTestCases extends FacebookTestParent {
    // create note.
    // add comment to note

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getNoteCommentsTestData");
        String msg = getTestRunMessageValue("msg").toString();
        String subject = getTestRunMessageValue("subject").toString();
        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);

        String noteid = publishNote(profileId, msg, subject);
        upsertOnTestRunMessage("note", noteid);

        String commentId = publishComment(noteid, msg);
        upsertOnTestRunMessage("commentId", commentId);
    }

    @Category({RegressionTests.class})
    @Test
    public void testGetNoteComments() {
        try {
            String commentId = getTestRunMessageValue("commentId").toString();
            String msg = getTestRunMessageValue("msg").toString();

            List<Comment> comments = runFlowAndGetPayload("get-note-comments");

            Boolean found = false;

            for (Comment comment : comments) {
                if (comment.getId().toString().equals(commentId)) {
                    assertEquals(msg, comment.getMessage());
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    //note cannot be deleted with delete object
    @After
    public void tearDown() throws Exception {
        String note = (String) getTestRunMessageValue("note");
        deleteObject(note);
    }
}
