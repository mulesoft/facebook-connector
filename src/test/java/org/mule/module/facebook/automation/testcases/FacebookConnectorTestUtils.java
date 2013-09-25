package org.mule.module.facebook.automation.testcases;

import org.mule.modules.tests.ConnectorTestUtils;

import com.restfb.DefaultJsonMapper;
import com.restfb.JsonMapper;
import com.restfb.json.JsonObject;

public class FacebookConnectorTestUtils extends ConnectorTestUtils {

	private static JsonMapper mapper = new DefaultJsonMapper();
	
	// Unused method. May need to start using it once discussion on CLDCONNECT-1104 is resolved.
	public static String getId(String json) {
		JsonObject obj = mapper.toJavaObject(json, JsonObject.class);
		return obj.getString("id");
	}
	
}
