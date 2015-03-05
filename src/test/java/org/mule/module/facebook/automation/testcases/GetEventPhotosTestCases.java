/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.Photo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class GetEventPhotosTestCases extends FacebookTestParent {

    @Before
    public void setUp() throws Exception {
        initializeTestRunMessage("getEventPhotosTestData");

        String profileId = getProfileId();
        String eventName = getTestRunMessageValue("eventName");
        String startTime = getTestRunMessageValue("startTime");

        String eventId = publishEvent(profileId, eventName, startTime);

        upsertOnTestRunMessage("profileId", profileId);
        upsertOnTestRunMessage("eventId", eventId);

        String photoFilePath = getTestRunMessageValue("photoFilePath");
        String caption = getTestRunMessageValue("caption");

        File photo = new File(getClass().getClassLoader().getResource(photoFilePath).toURI());

        String photoId = publishPhoto(eventId, caption, photo);
        upsertOnTestRunMessage("photoId", photoId);
    }

    @Category({RegressionTests.class})
    @Test
    public void testGetEventPhotos() {
        try {
            String photoId = getTestRunMessageValue("photoId");

            List<Photo> photos = runFlowAndGetPayload("get-event-photos");
            assertTrue(photos.size() == 1);

            Photo photo = photos.get(0);
            assertEquals(photo.getId(), photoId);
        } catch (Exception e) {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @After
    public void tearDown() throws Exception {
        String eventId = getTestRunMessageValue("eventId");
        deleteObject(eventId);
    }


}
