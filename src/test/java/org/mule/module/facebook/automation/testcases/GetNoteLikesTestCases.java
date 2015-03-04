/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.NamedFacebookType;
import com.restfb.types.Post.Likes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetNoteLikesTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getNoteLikesTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);

        String msg = getTestRunMessageValue("msg").toString();
        String subject = getTestRunMessageValue("subject").toString();

        String noteid = publishNote(getProfileId(), msg, subject);
        upsertOnTestRunMessage("note", noteid);

        like(noteid);
    }

    @Category({RegressionTests.class})
    @Test
    public void testGetNoteLikes() {
        try {
            Likes likes = runFlowAndGetPayload("get-note-likes");

            List<NamedFacebookType> data = likes.getData();
            assertTrue(data.size() == 1);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String noteId = (String) getTestRunMessageValue("note");
        deleteObject(noteId);
    }
}
