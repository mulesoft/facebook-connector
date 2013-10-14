/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
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
import org.mule.module.facebook.types.Photo;
import org.mule.modules.tests.TestParent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.restfb.types.Album;
import com.restfb.types.Comment;
import com.restfb.types.Event;
import com.restfb.types.Group;
import com.restfb.types.Link;
import com.restfb.types.Note;
import com.restfb.types.StatusMessage;
import com.restfb.types.User;

public class FacebookTestParent extends TestParent {

	protected static final String[] SPRING_CONFIG_FILES = new String[] { "AutomationSpringBeans.xml" };
	protected static ApplicationContext context;
	protected Map<String, Object> testObjects;

	// Set global timeout of tests to 10minutes
	@Rule
	public Timeout globalTimeout = new Timeout(600000);

	@Override
	protected String getConfigResources() {
		return "automation-test-flows.xml";
	}

    protected MessageProcessor lookupMessageProcessor(String name) {
        return (MessageProcessor) muleContext.getRegistry().lookupFlowConstruct(name);
    }

    @BeforeClass
    public static void beforeClass() {
    	context = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILES);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Before
    public void init() throws ObjectStoreException {
    	ObjectStore objectStore = muleContext.getRegistry().lookupObject(MuleProperties.DEFAULT_USER_OBJECT_STORE_NAME);
    	objectStore.store("accessTokenId", (FacebookConnectorOAuthState) context.getBean("connectorOAuthState"));
    	objectStore.store("accessTokenIdPage", (FacebookConnectorOAuthState) context.getBean("connectorOAuthStatePage"));
    }

	protected Album requestAlbum(String albumId) throws Exception {
		testObjects.put("album", albumId);

		MessageProcessor flow = lookupFlowConstruct("get-album");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (Album) response.getMessage().getPayload();
    }

    protected String publishAlbum(String albumName, String msg, String profileId) throws Exception {
    	testObjects.put("albumName", albumName);
    	testObjects.put("msg", msg);
    	testObjects.put("profileId", profileId);

  		MessageProcessor flow = lookupFlowConstruct("publish-album");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (String) response.getMessage().getPayload();
    }
    
    protected String publishAlbumOnPage(String albumName, String msg, String pageId) throws Exception {
    	testObjects.put("albumName", albumName);
    	testObjects.put("msg", msg);
    	testObjects.put("profileId", pageId);

  		MessageProcessor flow = lookupFlowConstruct("publish-album-on-page");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (String) response.getMessage().getPayload();
    }

	protected String publishMessage(String profileId, String msg) throws Exception {
		testObjects.put("profileId", profileId);
		testObjects.put("msg", msg);

		MessageProcessor flow = lookupFlowConstruct("publish-message");

		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (String) response.getMessage().getPayload();
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

	protected StatusMessage getStatus(String statusId) throws Exception {
		testObjects.put("status", statusId);

		MessageProcessor flow = lookupFlowConstruct("get-status");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (StatusMessage) response.getMessage().getPayload();
	}

	protected Album getAlbum(String albumId) throws Exception {
		testObjects.put("album", albumId);

		MessageProcessor flow = lookupFlowConstruct("get-album");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (Album) response.getMessage().getPayload();
	}

    protected User getLoggedUserDetails() throws Exception {
    	MessageProcessor flow = lookupFlowConstruct("logged-user-details");
    	MuleEvent response = flow.process(getTestEvent(testObjects));
    	return (User) response.getMessage().getPayload();
    }

    protected String getProfileId() throws Exception {
    	return getLoggedUserDetails().getId();
    }

    protected Photo getPhoto(String photoId, String metadata) throws Exception {
    	testObjects.put("photo", photoId);
    	testObjects.put("metadata", metadata);

    	MessageProcessor flow = lookupFlowConstruct("get-photo");
    	MuleEvent response = flow.process(getTestEvent(testObjects));
    	return (Photo) response.getMessage().getPayload();
    }

    protected String publishComment(String postId, String msg) throws Exception {
    	testObjects.put("postId", postId);
    	testObjects.put("msg", msg);

    	MessageProcessor flow = lookupFlowConstruct("publish-comment");
    	MuleEvent response = flow.process(getTestEvent(testObjects));
    	return (String) response.getMessage().getPayload();
    }

    protected String publishLink(String profileId, String msg, String link) throws Exception {
    	testObjects.put("profileId", profileId);
    	testObjects.put("msg", msg);
    	testObjects.put("link", link);

    	MessageProcessor flow = lookupFlowConstruct("publish-link");
    	MuleEvent response = flow.process(getTestEvent(testObjects));
    	return (String) response.getMessage().getPayload();
    }

    protected String publishPhoto(String albumId, String caption, File photo) throws Exception {
    	testObjects.put("albumId", albumId);
    	testObjects.put("caption", caption);
    	testObjects.put("photoRef", photo);

    	MessageProcessor flow = lookupFlowConstruct("publish-photo");
    	MuleEvent response = flow.process(getTestEvent(testObjects));
    	return (String) response.getMessage().getPayload();
    }

    public boolean like(String postId) throws Exception {
    	testObjects.put("postId", postId);

    	MessageProcessor flow = lookupFlowConstruct("like");
    	MuleEvent response = flow.process(getTestEvent(testObjects));
    	return (Boolean) response.getMessage().getPayload();
    }

    public boolean dislike(String postId) throws Exception {
    	testObjects.put("postId", postId);

    	MessageProcessor flow = lookupFlowConstruct("dislike");
    	MuleEvent response = flow.process(getTestEvent(testObjects));
    	return (Boolean) response.getMessage().getPayload();
    }

    public List<Comment> getStatusComments(String statusId) throws Exception {
    	return getStatusComments(statusId, "now", "yesterday", "100", "0");
    }

    @SuppressWarnings("unchecked")
	public List<Comment> getStatusComments(String statusId, String until, String since, String limit, String offset) throws Exception {
    	testObjects.put("status", statusId);
    	testObjects.put("until", until);
    	testObjects.put("since", since);
    	testObjects.put("limit", limit);
    	testObjects.put("offset", offset);

    	MessageProcessor flow = lookupFlowConstruct("get-status-comments");
    	MuleEvent response = flow.process(getTestEvent(testObjects));
    	return (List<Comment>) response.getMessage().getPayload();
    }

    protected Boolean deleteObject(String objectId) throws Exception {
    	testObjects.put("objectId", objectId);

    	MessageProcessor flow = lookupFlowConstruct("delete-object");
    	MuleEvent response = flow.process(getTestEvent(testObjects));
    	return (Boolean) response.getMessage().getPayload();
    }
    
    protected Boolean deletePageObject(String objectId) throws Exception {
    	testObjects.put("objectId", objectId);

    	MessageProcessor flow = lookupFlowConstruct("delete-object-from-page");
    	MuleEvent response = flow.process(getTestEvent(testObjects));
    	return (Boolean) response.getMessage().getPayload();
    }

    public Link getLink(String linkId) throws Exception{
    	testObjects.put("link", linkId);
    	MessageProcessor flow = lookupFlowConstruct("get-link");
    	MuleEvent response = flow.process(getTestEvent(testObjects));
    	Link myLink = (Link) response.getMessage().getPayload();
    	return myLink;
	}

    protected String publishEvent(String profileId, String eventName, String startTime) throws Exception {
    	testObjects.put("profileId", profileId);
    	testObjects.put("eventName", eventName);
    	testObjects.put("startTime", startTime);

    	MessageProcessor flow = lookupFlowConstruct("publish-event");
    	MuleEvent response = flow.process(getTestEvent(testObjects));
    	return (String) response.getMessage().getPayload();
    }

    protected String publishEventPage(String pageId, String eventName, String startTime) throws Exception {
    	testObjects.put("profileId", pageId);
    	testObjects.put("eventName", eventName);
    	testObjects.put("startTime", startTime);

    	MessageProcessor flow = lookupFlowConstruct("publish-event-on-page");
    	MuleEvent response = flow.process(getTestEvent(testObjects));
    	return (String) response.getMessage().getPayload();
    }

    protected String publishMessage(String profileId, String msg, String link, String linkName, String description, String picture, String caption, String place) throws Exception {
    	testObjects.put("profileId", profileId);
    	testObjects.put("msg", msg);
    	testObjects.put("link", link);
    	testObjects.put("linkName", linkName);
    	testObjects.put("description", description);
    	testObjects.put("picture", picture);
    	testObjects.put("caption", caption);
    	testObjects.put("place", place);

		MessageProcessor flow = lookupFlowConstruct("publish-message-all-attributes");

		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (String) response.getMessage().getPayload();
	}
    
	protected String publishNote(String profileId, String msg, String subject ) throws Exception{
		testObjects.put("profileId", profileId);
		testObjects.put("msg", msg);
		testObjects.put("subject", subject);

		MessageProcessor flow = lookupFlowConstruct("publish-note");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return response.getMessage().getPayloadAsString();
	}

	protected String publishNoteOnPage(String pageId, String msg, String subject ) throws Exception{
		testObjects.put("profileId", pageId);
		testObjects.put("msg", msg);
		testObjects.put("subject", subject);

		MessageProcessor flow = lookupFlowConstruct("publish-note-on-page");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return response.getMessage().getPayloadAsString();
	}

	protected Note getNote(String note) throws Exception {
		testObjects.put("note", note);

		MessageProcessor flow = lookupFlowConstruct("get-note");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (Note) response.getMessage().getPayload();
	}

	protected Boolean attendEvent(String eventId) throws Exception {
		testObjects.put("eventId", eventId);

		MessageProcessor flow = lookupFlowConstruct("attend-event");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (Boolean) response.getMessage().getPayload();
	}

	protected Boolean declineEvent(String eventId) throws Exception {
		testObjects.put("eventId", eventId);

		MessageProcessor flow = lookupFlowConstruct("decline-event");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (Boolean) response.getMessage().getPayload();
	}

	protected Event getEvent(String eventId) throws Exception {
		testObjects.put("eventId", eventId);

		MessageProcessor flow = lookupFlowConstruct("get-event");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (Event) response.getMessage().getPayload();
	}


	@SuppressWarnings("unchecked")
	protected List<Group> searchGroups(String query) throws Exception {
		testObjects.put("q", query);

		MessageProcessor flow = lookupFlowConstruct("search-groups");
		MuleEvent response = flow.process(getTestEvent(testObjects));
		return (List<Group>) response.getMessage().getPayload();

	}

	public static String today() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = GregorianCalendar.getInstance();
		return format.format(calendar.getTime());
	}

	public static String tomorrow() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return format.format(calendar.getTime());
	}

	public static String yesterday() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return format.format(calendar.getTime());
	}
}
