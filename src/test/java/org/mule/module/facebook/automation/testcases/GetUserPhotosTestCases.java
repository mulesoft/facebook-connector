package org.mule.module.facebook.automation.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.types.Photo;

public class GetUserPhotosTestCases extends FacebookTestParent {

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testObjects = (Map<String, Object>) context.getBean("getUserPhotosTestData");
		
		String profileId = getProfileId();
		testObjects.put("user", profileId);
		
		String caption = (String) testObjects.get("caption");
		File photoFile = new File(getClass().getClassLoader().getResource((String) testObjects.get("photoFilePath")).toURI());
		
		String photoId = publishPhoto(profileId, caption, photoFile);
		testObjects.put("photoId", photoId);
	}
	
	@SuppressWarnings("unchecked")
	@Category({RegressionTests.class})
	@Test
	public void testGetUserPhotos() {
		try {
			String photoId = (String) testObjects.get("photoId");
			
			MessageProcessor flow = lookupFlowConstruct("get-user-photos");
			MuleEvent response = flow.process(getTestEvent(testObjects));
			
			List<Photo> result = (List<Photo>) response.getMessage().getPayload();
			assertEquals(result.size(), 1);
			
			Photo photo = result.get(0);
			assertEquals(photo.getId(), photoId);
		}
		catch (Exception e) {
			fail(ConnectorTestUtils.getStackTrace(e));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		String photoId = (String) testObjects.get("photoId");
		deleteObject(photoId);
	}
	
}
