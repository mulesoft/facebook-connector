package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.module.facebook.types.Photo;

public class PublishPhotoTestCases extends FacebookTestParent {

	@Before
	public void setUp() {
		try {
			testObjects = (Map<String, Object>) context.getBean("publishPhotoTestData");
			
			String profileId = getProfileId();
			testObjects.put("profileId", profileId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Category({SmokeTests.class, RegressionTests.class})
	@Test
	public void testPublishPhoto() {
		try {
			String profileId = (String) testObjects.get("profileId");
			String caption = (String) testObjects.get("caption");
			File photoFile = new File(getClass().getClassLoader().getResource((String) testObjects.get("photoFilePath")).toURI());

			assertTrue(photoFile.getAbsolutePath() + " does not exist.", photoFile.exists());
			
			String photoId = publishPhoto(profileId, caption, photoFile);
			assertTrue(StringUtils.isNotEmpty(photoId));			

			testObjects.put("photoId", photoId);
			
			String metadata = (String) testObjects.get("metadata");
			
			Photo photo = getPhoto(photoId, metadata);
			assertEquals(photo.getId(), photoId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
	public void tearDown() {
		try {
			String photoId = (String) testObjects.get("photoId");
			deleteObject(photoId);
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
