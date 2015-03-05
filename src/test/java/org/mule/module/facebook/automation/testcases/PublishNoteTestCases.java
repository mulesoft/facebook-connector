/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Note;
import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PublishNoteTestCases extends FacebookTestParent {

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("publishNoteTestData");

        String profileId = getProfileId();
        upsertOnTestRunMessage("profileId", profileId);
    }

    @Category({SmokeTests.class, RegressionTests.class})
    @Test
    public void testPublishNote() {
        try {
            String id = runFlowAndGetPayload("publish-note");

            assertTrue(StringUtils.isNotEmpty(id));
            upsertOnTestRunMessage("noteId", id);

            Note note = getNote(id);
            assertTrue(note.getId().equals(id));
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String id = (String) getTestRunMessageValue("noteId");
        deleteObject(id);
    }
}
