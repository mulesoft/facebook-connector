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

public class GetPhotoTagsTestCases extends FacebookTestParent {

	@Before
	public void setUp() throws Exception {
		
		String profileId = getProfileId();
		String auxProfileId = getProfileIdAux();

		upsertOnTestRunMessage("profileId", profileId);
		upsertOnTestRunMessage("auxProfileId", auxProfileId);
		
		File photo = new File(getClass().getClassLoader().getResource("image.jpg").toURI());
		
		String photoId = publishPhoto(profileId, "My Photo", photo);
		upsertOnTestRunMessage("photoId", photoId);
		
		tagPhoto(photoId, auxProfileId);
	}
	
	@Category({RegressionTests.class})
	@Test
	public void testGetPhotoTags() {
		try {
			String auxProfileId = getTestRunMessageValue("auxProfileId");
			String photoId = getTestRunMessageValue("photoId");
			
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
