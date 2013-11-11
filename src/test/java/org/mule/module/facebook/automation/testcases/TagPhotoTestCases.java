package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Photo.Tag;

public class TagPhotoTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		initializeTestRunMessage("tagPhotoTestData");
		
		String profileId = getProfileId();
		String auxProfileId = getProfileIdAux();

		upsertOnTestRunMessage("profileId", profileId);
		upsertOnTestRunMessage("auxProfileId", auxProfileId);
		
		String photoFilePath = getTestRunMessageValue("photoFilePath");
		String caption = getTestRunMessageValue("caption");
		
		File photo = new File(getClass().getClassLoader().getResource(photoFilePath).toURI());
		
		String photoId = publishPhoto(profileId, caption, photo);
		upsertOnTestRunMessage("photoId", photoId);
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testTagPhoto() {
		try {
			String auxProfileId = getTestRunMessageValue("auxProfileId");
			String photoId = getTestRunMessageValue("photoId");
			
			Boolean result = tagPhoto(photoId, auxProfileId);
			assertTrue(result);
			
			List<Tag> photoTags = getPhotoTags(photoId);
			assertTrue(photoTags.size() == 1);
			
			Tag tag = photoTags.get(0);
			assertEquals(tag.getId(), auxProfileId);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String photoId = getTestRunMessageValue("photoId");
		deleteObject(photoId);
	}
}
