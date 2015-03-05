/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.automation.testcases;

import com.restfb.types.*;
import com.restfb.types.Photo.Tag;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.mule.api.config.MuleProperties;
import org.mule.api.store.ObjectStore;
import org.mule.api.store.ObjectStoreException;
import org.mule.common.security.oauth.OAuthState;
import org.mule.module.facebook.types.Photo;
import org.mule.modules.tests.ConnectorTestCase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class FacebookTestParent extends ConnectorTestCase {

    // Set global timeout of tests to 10minutes
    @Rule
    public Timeout globalTimeout = new Timeout(600000);

    private Properties properties = new Properties();

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

    /*
     * Returns back a list of strings from the input array.
     * The different between this method and Arrays.asList() is that elements are trimmed before
     * they are placed in the list.
     */
    private static List<String> toList(String[] array) {
        List<String> list = new ArrayList<String>();
        for (String element : array) {
            list.add(element.trim());
        }
        return list;
    }

    @Override
    protected <T> T runFlowAndGetPayload(String flowName) throws Exception {
        return super.runFlowAndGetPayload(flowName);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Before
    public void init() throws ObjectStoreException, IOException {

        ObjectStore objectStore = muleContext.getRegistry().lookupObject(MuleProperties.DEFAULT_USER_OBJECT_STORE_NAME);
        if (objectStore.contains("accessTokenId"))
            objectStore.remove("accessTokenId");
        if (objectStore.contains("accessTokenIdPage"))
            objectStore.remove("accessTokenIdPage");
        if (objectStore.contains("accessTokenIdAux"))
            objectStore.remove("accessTokenIdAux");

        objectStore.store("accessTokenId", (OAuthState) getBeanFromContext("connectorOAuthState"));
        objectStore.store("accessTokenIdPage", (OAuthState) getBeanFromContext("connectorOAuthStatePage"));
        objectStore.store("accessTokenIdAux", (OAuthState) getBeanFromContext("connectorOAuthStateAux"));

        InputStream props = getClass().getClassLoader().getResourceAsStream("init-state.properties");
        properties.load(props);
    }

    protected Album requestAlbum(String albumId) throws Exception {
        upsertOnTestRunMessage("album", albumId);

        return runFlowAndGetPayload("get-album");
    }

    protected String publishAlbum(String albumName, String msg, String profileId) throws Exception {
        upsertOnTestRunMessage("albumName", albumName);
        upsertOnTestRunMessage("msg", msg);
        upsertOnTestRunMessage("profileId", profileId);

        return runFlowAndGetPayload("publish-album");
    }

    protected String publishAlbumOnPage(String albumName, String msg, String pageId) throws Exception {
        upsertOnTestRunMessage("albumName", albumName);
        upsertOnTestRunMessage("msg", msg);
        upsertOnTestRunMessage("profileId", pageId);

        return runFlowAndGetPayload("publish-album-on-page");
    }

    protected String publishMessage(String profileId, String msg) throws Exception {
        upsertOnTestRunMessage("profileId", profileId);
        upsertOnTestRunMessage("msg", msg);

        return runFlowAndGetPayload("publish-message");
    }

    @SuppressWarnings("unchecked")
    protected Collection<Album> getUserAlbums(String user, String since, String until, String limit, String offset) throws Exception {
        upsertOnTestRunMessage("user", user);
        upsertOnTestRunMessage("since", since);
        upsertOnTestRunMessage("until", until);
        upsertOnTestRunMessage("limit", limit);
        upsertOnTestRunMessage("offset", offset);

        return runFlowAndGetPayload("get-user-albums");
    }

    protected StatusMessage getStatus(String statusId) throws Exception {
        upsertOnTestRunMessage("status", statusId);

        return runFlowAndGetPayload("get-status");
    }

    protected Album getAlbum(String albumId) throws Exception {
        upsertOnTestRunMessage("album", albumId);

        return runFlowAndGetPayload("get-album");
    }

    protected User getLoggedUserDetails() throws Exception {
        return runFlowAndGetPayload("logged-user-details");
    }

    protected String getProfileId() throws Exception {
        return getLoggedUserDetails().getId();
    }

    protected Photo getPhoto(String photoId, String metadata) throws Exception {
        upsertOnTestRunMessage("photo", photoId);
        upsertOnTestRunMessage("metadata", metadata);

        return runFlowAndGetPayload("get-photo");
    }

    protected String publishComment(String postId, String msg) throws Exception {
        upsertOnTestRunMessage("postId", postId);
        upsertOnTestRunMessage("msg", msg);

        return runFlowAndGetPayload("publish-comment");
    }

    protected String publishLink(String profileId, String msg, String link) throws Exception {
        upsertOnTestRunMessage("profileId", profileId);
        upsertOnTestRunMessage("msg", msg);
        upsertOnTestRunMessage("link", link);

        return runFlowAndGetPayload("publish-link");
    }

    protected String publishPhoto(String albumId, String caption, File photo) throws Exception {
        upsertOnTestRunMessage("albumId", albumId);
        upsertOnTestRunMessage("caption", caption);
        upsertOnTestRunMessage("photoRef", photo);

        return runFlowAndGetPayload("publish-photo");
    }

    protected String publishVideo(String id, String title, String description, File video) throws Exception {
        upsertOnTestRunMessage("id", id);
        upsertOnTestRunMessage("title", title);
        upsertOnTestRunMessage("description", description);
        upsertOnTestRunMessage("videoRef", video);

        return runFlowAndGetPayload("publish-video");
    }

    public Boolean like(String postId) throws Exception {
        upsertOnTestRunMessage("postId", postId);
        return runFlowAndGetPayload("like");
    }

    public Boolean dislike(String postId) throws Exception {
        upsertOnTestRunMessage("postId", postId);

        return runFlowAndGetPayload("dislike");
    }

    public List<Comment> getStatusComments(String statusId) throws Exception {
        return getStatusComments(statusId, "now", "yesterday", "100", "0");
    }

    @SuppressWarnings("unchecked")
    public List<Comment> getStatusComments(String statusId, String until, String since, String limit, String offset) throws Exception {
        upsertOnTestRunMessage("status", statusId);
        upsertOnTestRunMessage("until", until);
        upsertOnTestRunMessage("since", since);
        upsertOnTestRunMessage("limit", limit);
        upsertOnTestRunMessage("offset", offset);

        return runFlowAndGetPayload("get-status-comments");
    }

    protected Group getGroup(String groupId) throws Exception {
        upsertOnTestRunMessage("group", groupId);

        return runFlowAndGetPayload("get-group");
    }

    protected Boolean deleteObject(String objectId) throws Exception {
        upsertOnTestRunMessage("objectId", objectId);
        return runFlowAndGetPayload("delete-object");
    }

    protected Boolean deletePageObject(String objectId) throws Exception {
        upsertOnTestRunMessage("objectId", objectId);

        return runFlowAndGetPayload("delete-object-from-page");
    }

    public Link getLink(String linkId) throws Exception {
        upsertOnTestRunMessage("link", linkId);

        return runFlowAndGetPayload("get-link");
    }

    protected String publishEvent(String profileId, String eventName, String startTime) throws Exception {
        upsertOnTestRunMessage("profileId", profileId);
        upsertOnTestRunMessage("eventName", eventName);
        upsertOnTestRunMessage("startTime", startTime);

        return runFlowAndGetPayload("publish-event");
    }

    protected String publishEventPage(String pageId, String eventName, String startTime) throws Exception {
        upsertOnTestRunMessage("profileId", pageId);
        upsertOnTestRunMessage("eventName", eventName);
        upsertOnTestRunMessage("startTime", startTime);

        return runFlowAndGetPayload("publish-event-on-page");
    }

    protected String publishMessage(String profileId, String msg, String link, String linkName, String description, String picture, String caption, String place) throws Exception {
        upsertOnTestRunMessage("profileId", profileId);
        upsertOnTestRunMessage("msg", msg);
        upsertOnTestRunMessage("link", link);
        upsertOnTestRunMessage("linkName", linkName);
        upsertOnTestRunMessage("description", description);
        upsertOnTestRunMessage("picture", picture);
        upsertOnTestRunMessage("caption", caption);
        upsertOnTestRunMessage("place", place);

        return runFlowAndGetPayload("publish-message-all-attributes");
    }

    protected String publishNote(String profileId, String msg, String subject) throws Exception {
        upsertOnTestRunMessage("profileId", profileId);
        upsertOnTestRunMessage("msg", msg);
        upsertOnTestRunMessage("subject", subject);

        return runFlowAndGetPayload("publish-note");
    }

    protected String publishNoteOnPage(String pageId, String msg, String subject) throws Exception {
        upsertOnTestRunMessage("profileId", pageId);
        upsertOnTestRunMessage("msg", msg);
        upsertOnTestRunMessage("subject", subject);

        return runFlowAndGetPayload("publish-note-on-page");
    }

    protected Note getNote(String note) throws Exception {
        upsertOnTestRunMessage("note", note);

        return runFlowAndGetPayload("get-note");
    }

    protected Boolean attendEvent(String eventId) throws Exception {
        upsertOnTestRunMessage("eventId", eventId);

        return runFlowAndGetPayload("attend-event");
    }

    protected Boolean declineEvent(String eventId) throws Exception {
        upsertOnTestRunMessage("eventId", eventId);

        return runFlowAndGetPayload("decline-event");
    }

    protected Event getEvent(String eventId) throws Exception {
        upsertOnTestRunMessage("eventId", eventId);

        return runFlowAndGetPayload("get-event");
    }

    protected Boolean tentativeEvent(String eventId) throws Exception {
        upsertOnTestRunMessage("eventId", eventId);

        return runFlowAndGetPayload("tentative-event");
    }

    public Boolean inviteUser(String eventId, String userId) throws Exception {
        upsertOnTestRunMessage("eventId", eventId);
        upsertOnTestRunMessage("userId", userId);

        return runFlowAndGetPayload("invite-user");
    }

    public Boolean inviteUserAux(String eventId, String userId) throws Exception {
        upsertOnTestRunMessage("eventId", eventId);
        upsertOnTestRunMessage("userId", userId);

        return runFlowAndGetPayload("invite-user-aux");
    }

    public Boolean tagPhoto(String photoId, String to) throws Exception {
        return tagPhoto(photoId, to, null, null, null);
    }

    public Boolean tagPhoto(String photoId, String to, String tagText, Integer x, Integer y) throws Exception {
        upsertOnTestRunMessage("photoId", photoId);
        upsertOnTestRunMessage("to", to);
        upsertOnTestRunMessage("tagText", tagText);
        upsertOnTestRunMessage("x", x);
        upsertOnTestRunMessage("y", y);

        return runFlowAndGetPayload("tag-photo");
    }

    public Boolean setPagePicture(String pageId, String imageUrl) throws Exception {
        upsertOnTestRunMessage("page", pageId);
        upsertOnTestRunMessage("imageUrl", imageUrl);

        return runFlowAndGetPayload("set-page-picture-from-link");
    }

    public Boolean setPagePicture(String pageId, File imageFile) throws Exception {
        upsertOnTestRunMessage("page", pageId);
        upsertOnTestRunMessage("sourceRef", imageFile);

        return runFlowAndGetPayload("set-page-picture-from-source");
    }

    public List<Tag> getPhotoTags(String photoId) throws Exception {
        upsertOnTestRunMessage("photoId", photoId);

        return runFlowAndGetPayload("get-photo-tags");
    }

    public Boolean uninviteUser(String eventId, String userId) throws Exception {
        upsertOnTestRunMessage("eventId", eventId);
        upsertOnTestRunMessage("userId", userId);

        return runFlowAndGetPayload("uninvite-user");
    }

    public List<User> getEventInvited(String eventId, String until, String since, String limit, String offset) throws Exception {
        upsertOnTestRunMessage("eventId", eventId);
        upsertOnTestRunMessage("until", until);
        upsertOnTestRunMessage("since", since);
        upsertOnTestRunMessage("limit", limit);
        upsertOnTestRunMessage("offset", offset);

        return runFlowAndGetPayload("get-event-invited");
    }

    @SuppressWarnings("unchecked")
    protected List<Group> searchGroups(String query) throws Exception {
        upsertOnTestRunMessage("q", query);

        return runFlowAndGetPayload("search-groups");

    }

    /*
     * Gets the expected IDs of Music pages which the test account has liked
     * An an example, the Metallica and Deep Purple page IDs are 10212595263, 192121640808384 respectively
     * So, inside the properties file, you would need to have the following configuration:
     *
     * facebook.init.music=10212595263,192121640808384
     *
     * This method would then return the following list: ["10212595263", "192121640808384"]
     */
    protected List<String> getExpectedMusic() throws IOException {
        return parsePropertyList("facebook.init.music");
    }

    /*
     * Gets the expected IDs of television show pages which the test account has liked
     * As an example, the Seinfeld and Top Gear UK page IDs are 9023924452, 133612723434302 respectively
     * So, inside the properties file, you would need to have the following configuration:
     *
     * facebook.init.television=9023924452,133612723434302
     *
     * This method would then return the following list: ["9023924452","133612723434302"]
     */
    protected List<String> getExpectedTelevision() throws IOException {
        return parsePropertyList("facebook.init.television");
    }

    /*
     * Gets the expected IDs of book pages which the test account has liked
     * As an example, the Harry Potter and A Game of Thrones - A Song of Ice and Fire page IDs are 107641979264998, 402991343106786 respectively
     * So, inside the properties file, you need to have the following configuration:
     *
     * facebook.init.books=107641979264998,402991343106786
     *
     * This method would then return the following list: ["107641979264998","402991343106786"]
     */
    protected List<String> getExpectedBooks() throws IOException {
        return parsePropertyList("facebook.init.books");
    }

    /*
     * Gets the expected IDs of book pages which the test account has liked
     * As an example, the Harry Potter and A Game of Thrones - A Song of Ice and Fire page IDs are 107641979264998, 402991343106786 respectively
     * So, inside the properties file, you need to have the following configuration:
     *
     * facebook.init.books=107641979264998,402991343106786
     *
     * This method would then return the following list: ["107641979264998","402991343106786"]
     */
    protected List<String> getExpectedMovies() throws IOException {
        return parsePropertyList("facebook.init.movies");
    }

    /*
     * This method collates the result of every one of the above four methods and returns them as a single list
     */
    protected List<String> getExpectedLikes() throws IOException {
        List<String> music = getExpectedMusic();
        List<String> television = getExpectedTelevision();
        List<String> books = getExpectedBooks();
        List<String> movies = getExpectedMovies();

        List<String> finalList = new ArrayList<String>();
        finalList.addAll(music);
        finalList.addAll(television);
        finalList.addAll(books);
        finalList.addAll(movies);
        return finalList;
    }

    protected String getExpectedGroupId() {
        String expectedGroupId = properties.getProperty("facebook.init.groupId");
        return expectedGroupId;
    }

	/*
     * ========================================================================
	 * 				A U X I L I A R Y   M E T H O D S    B E L O W
	 * ========================================================================
	 */

    protected User getLoggedUserDetailsAux() throws Exception {
        return runFlowAndGetPayload("logged-user-details-aux");
    }

    protected String getProfileIdAux() throws Exception {
        return getLoggedUserDetailsAux().getId();
    }

    protected Boolean deleteObjectAux(String objectId) throws Exception {
        upsertOnTestRunMessage("objectId", objectId);

        return runFlowAndGetPayload("delete-object-aux");
    }

    protected String publishEventAux(String profileId, String eventName, String startTime) throws Exception {
        upsertOnTestRunMessage("profileId", profileId);
        upsertOnTestRunMessage("eventName", eventName);
        upsertOnTestRunMessage("startTime", startTime);

        return runFlowAndGetPayload("publish-event-aux");
    }

    protected List<String> parsePropertyList(String propertyKey) {
        String propertyValue = properties.getProperty(propertyKey);
        List<String> result;
        if (propertyValue.isEmpty()) { // split() on an empty list returns a list of size 1
            result = new ArrayList<String>();
        } else {
            result = toList(propertyValue.split(","));
        }
        return result;
    }
}
