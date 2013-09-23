/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.mule.api.MuleEvent;
import org.mule.api.config.MuleProperties;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.store.ObjectStore;
import org.mule.api.store.ObjectStoreException;
import org.mule.module.facebook.oauth.FacebookConnectorOAuthState;
import org.mule.modules.tests.TestParent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.restfb.types.Album;



public class FacebookTestParent extends TestParent {
	
	protected static final String[] SPRING_CONFIG_FILES = new String[] {"AutomationSpringBeans.xml"};
	protected static ApplicationContext context;
	protected Map<String,Object> testObjects;

	// Set global timeout of tests to 10minutes
    @Rule
    public Timeout globalTimeout = new Timeout(600000);
	
	@Override
	protected String getConfigResources() {
		return "automation-test-flows.xml";
	}
	
    @BeforeClass
    public static void beforeClass() {
    	
    	context = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILES);
    }
    
    @Before
    public void init() throws ObjectStoreException {
    	
    	ObjectStore objectStore = muleContext.getRegistry().lookupObject(MuleProperties.DEFAULT_USER_OBJECT_STORE_NAME);
    	objectStore.store("accessTokenId", (FacebookConnectorOAuthState) context.getBean("connectorOAuthState"));

    }
    
    protected Album requestAlbum(String albumId) throws Exception {
    	testObjects.put("album", albumId);
    	
		MessageProcessor flow = lookupFlowConstruct("get-album");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (Album) response.getMessage().getPayload();
    }
    
    protected void publishAlbum(String albumName, String msg, String profileId) throws Exception {
    	testObjects.put("albumName", albumName);
    	testObjects.put("msg", msg);
    	testObjects.put("profileId", profileId);
    	
		MessageProcessor flow = lookupFlowConstruct("publish-album");
		flow.process(getTestEvent(testObjects));
    }
    
    @SuppressWarnings("unchecked")
	protected Collection<Album> requestUserAlbums(String user, String since, String until, String limit, String offset) throws Exception {
    	testObjects.put("user", user);
    	testObjects.put("since", since);
    	testObjects.put("until", until);
    	testObjects.put("limit", limit);
    	testObjects.put("offset", offset);
    	
		MessageProcessor flow = lookupFlowConstruct("get-user-albums");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (Collection<Album>) response.getMessage().getPayload();
    }
    
}