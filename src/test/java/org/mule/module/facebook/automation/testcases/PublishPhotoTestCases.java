package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.facebook.types.Photo;
import org.mule.modules.tests.ConnectorTestUtils;

public class PublishPhotoTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("publishPhotoTestData");
			
		String profileId = getProfileId();
		upsertOnTestRunMessage("profileId", profileId);
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testPublishPhoto() {
		try {
			String profileId = (String) getTestRunMessageValue("profileId");
			String caption = (String) getTestRunMessageValue("caption");
			File photoFile = new File(getClass().getClassLoader().getResource((String) getTestRunMessageValue("photoFilePath")).toURI());

			assertTrue(photoFile.getAbsolutePath() + " does not exist.", photoFile.exists());
			
			String photoId = publishPhoto(profileId, caption, photoFile);
			assertTrue(StringUtils.isNotEmpty(photoId));			

			upsertOnTestRunMessage("photoId", photoId);
			
			String metadata = (String) getTestRunMessageValue("metadata");
			
			Photo photo = getPhoto(photoId, metadata);
			assertEquals(photo.getId(), photoId);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String photoId = (String) getTestRunMessageValue("photoId");
		deleteObject(photoId);
	}
	
}
