package org.mule.module.faceboork;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.OAuth;
import org.mule.tools.cloudconnect.annotations.OAuthAccessToken;
import org.mule.tools.cloudconnect.annotations.OAuthAccessTokenExpiration;
import org.mule.tools.cloudconnect.annotations.OAuthAuthorizationCode;
import org.mule.tools.cloudconnect.annotations.OAuthClientId;
import org.mule.tools.cloudconnect.annotations.OAuthClientSecret;
import org.mule.tools.cloudconnect.annotations.OAuthRedirectUri;
import org.mule.tools.cloudconnect.annotations.OAuthScope;
import org.mule.tools.cloudconnect.annotations.OAuthVersion;
import org.mule.tools.cloudconnect.annotations.Operation;
import org.mule.tools.cloudconnect.annotations.Parameter;
import org.mule.tools.cloudconnect.annotations.Property;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.net.URI;

@Connector(namespacePrefix = "facebook")
@OAuth(version = OAuthVersion.OA20,
        accessTokenUrl = "https://graph.facebook.com/oauth/access_token",
        authorizationUrl = "https://graph.facebook.com/oauth/authorize")
public class FacebookConnector {
    private static String FACEBOOK_URI = "https://graph.facebook.com";

    @Property
    @OAuthClientId
    private String appId;

    @Property
    @OAuthClientSecret
    private String appSecret;

    @Property
    @OAuthRedirectUri
    private String redirectUri;

    @Property(optional = true, defaultValue = "email,read_stream,publish_stream")
    @OAuthScope
    private String scope;

    @OAuthAuthorizationCode
    private String authorizationCode;

    @OAuthAccessToken
    private String accessToken;

    @OAuthAccessTokenExpiration
    private long accessTokenExpiration;

    /**
     * Jersey client
     */
    private Client client;

    /**
     * Constructor
     */
    public FacebookConnector() {
        this.client = new Client();

        this.client.addFilter(new LoggingFilter());
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getScope() {
        return scope;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public void setAccessTokenExpiration(long accessTokenExpiration) {
        this.accessTokenExpiration = accessTokenExpiration;
    }

    /**
     * Search over all public objects in the social graph
     * <p/>
     * {@code
     * <facebook:search q="muelsoft" obj="photo"/>
     * }
     *
     * @param q   The search string
     * @param obj Supports these types of objects: All public posts (post), people (user), pages (page), events
     *            (event), groups (group), check-ins (checkin)
     */
    @Operation
    public String search(String q, @Parameter(optional = true, defaultValue = "post") String obj) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("search").build();
        WebResource resource = client.resource(uri);
        resource.queryParam("q", q);
        resource.queryParam("object", obj);

        return resource.get(String.class);
    }

    /**
     * A photo album
     * <p/>
     * {@code
     * <facebook:get-album album="123456789"/>
     * }
     *
     * @param album    Represents the ID of the album object.
     * @param metadata The Graph API supports introspection of objects, which enables you to see all of the connections
     *                 an object has without knowing its type ahead of time.
     */
    @Operation
    public String getAlbum(String album, @Parameter(optional = true, defaultValue = "0") String metadata) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{album}").build(album);
        WebResource resource = client.resource(uri);
        resource.queryParam("metadata", metadata);

        return resource.get(String.class);
    }

    /**
     * The photos contained in this album
     * <p/>
     * {@code
     * <facebook:get-album-photos album="123456789"/>
     * }
     *
     * @param album  Represents the ID of the album object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getAlbumPhotos(String album, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{album}/photos").build(album);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The comments made on this album
     * <p/>
     * {@code
     * <facebook:get-album-comments album="123456789"/>
     * }
     *
     * @param album  Represents the ID of the album object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getAlbumComments(String album, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{album}/comments").build(album);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * Specifies information about an event, including the location, event name, and which invitees plan
     * to attend.
     * <p/>
     * {@code
     * <facebook:get-event eventId="123456789" metadata="O"/>
     * }
     *
     * @param eventId  Represents the ID of the event object.
     * @param metadata The Graph API supports introspection of objects, which enables you to see all of the connections
     *                 an object has without knowing its type ahead of time.
     */
    @Operation
    public String getEvent(String eventId, @Parameter(optional = true, defaultValue = "0") String metadata) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{event}").build(eventId);
        WebResource resource = client.resource(uri);
        resource.queryParam("metadata", metadata);

        return resource.get(String.class);
    }

    /**
     * This event's wall
     * <p/>
     * {@code
     * <facebook:get-event-wall eventId="123456789"/>
     * }
     *
     * @param eventId Represents the ID of the event object.
     * @param since   A unix timestamp or any date accepted by strtotime
     * @param until   A unix timestamp or any date accepted by strtotime
     * @param limit   Limit the number of items returned.
     * @param offset  An offset to the response. Useful for paging.
     */
    @Operation
    public String getEventWall(String eventId, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{event}/feed").build(eventId);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * All of the users who have been not yet responded to their invitation to this event
     * <p/>
     * {@code
     * <facebook:get-event-no-reply eventId="123456789"/>
     * }
     *
     * @param eventId Represents the ID of the event object.
     * @param since   A unix timestamp or any date accepted by strtotime
     * @param until   A unix timestamp or any date accepted by strtotime
     * @param limit   Limit the number of items returned.
     * @param offset  An offset to the response. Useful for paging.
     */
    @Operation
    public String getEventNoReply(String eventId, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{event}/noreply").build(eventId);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * All of the users who have been responded "Maybe" to their invitation to this event
     * <p/>
     * {@code
     * <facebook:get-event-maybe eventId="123456789"/>
     * }
     *
     * @param eventId Represents the ID of the event object.
     * @param since   A unix timestamp or any date accepted by strtotime
     * @param until   A unix timestamp or any date accepted by strtotime
     * @param limit   Limit the number of items returned.
     * @param offset  An offset to the response. Useful for paging.
     */
    @Operation
    public String getEventMaybe(String eventId, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{event}/maybe").build(eventId);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * All of the users who have been invited to this event
     * <p/>
     * {@code
     * <facebook:get-event-invited eventId="123456789"/>
     * }
     *
     * @param eventId Represents the ID of the event object.
     * @param since   A unix timestamp or any date accepted by strtotime
     * @param until   A unix timestamp or any date accepted by strtotime
     * @param limit   Limit the number of items returned.
     * @param offset  An offset to the response. Useful for paging.
     */
    @Operation
    public String getEventInvited(String eventId, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{event}/invited").build(eventId);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * All of the users who are attending this event
     * <p/>
     * {@code
     * <facebook:get-event-attending eventId="123456789"/>
     * }
     *
     * @param eventId Represents the ID of the event object.
     * @param since   A unix timestamp or any date accepted by strtotime
     * @param until   A unix timestamp or any date accepted by strtotime
     * @param limit   Limit the number of items returned.
     * @param offset  An offset to the response. Useful for paging.
     */
    @Operation
    public String getEventAttending(String eventId, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{event}/attending").build(eventId);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * All of the users who declined their invitation to this event
     *
     * @param eventId Represents the ID of the event object.
     * @param since   A unix timestamp or any date accepted by strtotime
     * @param until   A unix timestamp or any date accepted by strtotime
     * @param limit   Limit the number of items returned.
     * @param offset  An offset to the response. Useful for paging.
     */
    @Operation
    public String getEventDeclined(String eventId, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{event}/declined").build(eventId);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The event's profile picture
     *
     * @param eventId Represents the ID of the event object.
     * @param type    One of square (50x50), small (50 pixels wide, variable height), and large (about 200 pixels wide,
     *                variable height)
     */
    @Operation
    public String getEventPicture(String eventId, @Parameter(optional = true, defaultValue = "small") String type) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{event}/picture").build(eventId);
        WebResource resource = client.resource(uri);
        resource.queryParam("type", type);

        return resource.get(String.class);
    }

    /**
     * A Facebook group
     *
     * @param group    Represents the ID of the group object.
     * @param metadata The Graph API supports introspection of objects, which enables you to see all of the connections
     *                 an object has without knowing its type ahead of time.
     */
    @Operation
    public String getGroup(String group, @Parameter(optional = true, defaultValue = "0") String metadata) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{group}").build(group);
        WebResource resource = client.resource(uri);
        resource.queryParam("metadata", metadata);

        return resource.get(String.class);
    }

    /**
     * This group's wall
     *
     * @param group  Represents the ID of the group object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getGroupWall(String group, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{group}/feed").build(group);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * All of the users who are members of this group
     *
     * @param group  Represents the ID of the group object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getGroupMembers(String group, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{group}/members").build(group);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The profile picture of this group
     *
     * @param group Represents the ID of the group object.
     * @param type  One of square (50x50), small (50 pixels wide, variable height), and large (about 200 pixels wide,
     *              variable height)
     */
    @Operation
    public String getGroupPicture(String group, @Parameter(optional = true, defaultValue = "small") String type) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{group}/picture").build(group);
        WebResource resource = client.resource(uri);
        resource.queryParam("type", type);

        return resource.get(String.class);
    }

    /**
     * A link shared on a user's wall
     *
     * @param link     Represents the ID of the link object.
     * @param metadata The Graph API supports introspection of objects, which enables you to see all of the connections
     *                 an object has without knowing its type ahead of time.
     */
    @Operation
    public String getLink(String link, @Parameter(optional = true, defaultValue = "0") String metadata) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{link}").build(link);
        WebResource resource = client.resource(uri);
        resource.queryParam("metadata", metadata);

        return resource.get(String.class);
    }

    /**
     * All of the comments on this link
     *
     * @param link   Represents the ID of the link object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getLinkComments(String link, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{link}/comments").build(link);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * A Facebook note
     *
     * @param note     Represents the ID of the note object.
     * @param metadata The Graph API supports introspection of objects, which enables you to see all of the connections
     *                 an object has without knowing its type ahead of time.
     */
    @Operation
    public String getNote(String note, @Parameter(optional = true, defaultValue = "0") String metadata) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{note}").build(note);
        WebResource resource = client.resource(uri);
        resource.queryParam("metadata", metadata);

        return resource.get(String.class);
    }

    /**
     * All of the comments on this note
     *
     * @param note   Represents the ID of the note object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getNoteComments(String note, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{note}/comments").build(note);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * People who like the note
     *
     * @param note   Represents the ID of the note object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getNoteLikes(String note, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{note}/likes").build(note);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * A
     *
     * @param page     Represents the ID of the page object.
     * @param metadata The Graph API supports introspection of objects, which enables you to see all of the connections
     *                 an object has without knowing its type ahead of time.
     */
    @Operation
    public String getPage(String page, @Parameter(optional = true, defaultValue = "0") String metadata) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}").build(page);
        WebResource resource = client.resource(uri);
        resource.queryParam("metadata", metadata);

        return resource.get(String.class);
    }

    /**
     * The page's wall
     *
     * @param page   Represents the ID of the page object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getPageWall(String page, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/feed").build(page);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The page's profile picture
     *
     * @param page Represents the ID of the page object.
     * @param type One of square (50x50), small (50 pixels wide, variable height), and large (about 200 pixels wide,
     *             variable height)
     */
    @Operation
    public String getPagePicture(String page, @Parameter(optional = true, defaultValue = "small") String type) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/picture").build(page);
        WebResource resource = client.resource(uri);
        resource.queryParam("type", type);

        return resource.get(String.class);
    }

    /**
     * The photos, videos, and posts in which this page has been tagged
     *
     * @param page   Represents the ID of the page object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getPageTagged(String page, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/tagged").build(page);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The page's posted links
     *
     * @param page   Represents the ID of the page object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getPageLinks(String page, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/links").build(page);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The photos this page has uploaded
     *
     * @param page   Represents the ID of the page object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getPagePhotos(String page, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/photos").build(page);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The groups this page is a member of
     *
     * @param page   Represents the ID of the page object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getPageGroups(String page, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/groups").build(page);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The photo albums this page has created
     *
     * @param page   Represents the ID of the page object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getPageAlbums(String page, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/albums").build(page);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The page's status updates
     *
     * @param page   Represents the ID of the page object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getPageStatuses(String page, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/statuses").build(page);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The videos this page has created
     *
     * @param page   Represents the ID of the page object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getPageVideos(String page, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/videos").build(page);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The page's notes
     *
     * @param page   Represents the ID of the page object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getPageNotes(String page, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/notes").build(page);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The page's own posts
     *
     * @param page   Represents the ID of the page object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getPagePosts(String page, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/posts").build(page);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The events this page is attending
     *
     * @param page   Represents the ID of the page object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getPageEvents(String page, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/events").build(page);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * Checkins made by the friends of the current session user
     *
     * @param page   Represents the ID of the page object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getPageCheckins(String page, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/checkins").build(page);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * An individual photo
     *
     * @param photo    Represents the ID of the photo object.
     * @param metadata The Graph API supports introspection of objects, which enables you to see all of the connections
     *                 an object has without knowing its type ahead of time.
     */
    @Operation
    public String getPhoto(String photo, @Parameter(optional = true, defaultValue = "0") String metadata) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{photo}").build(photo);
        WebResource resource = client.resource(uri);
        resource.queryParam("metadata", metadata);

        return resource.get(String.class);
    }

    /**
     * All of the comments on this photo
     *
     * @param photo  Represents the ID of the photo object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getPhotoComments(String photo, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{photo}/comments").build(photo);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * People who like the photo
     *
     * @param photo  Represents the ID of the photo object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getPhotoLikes(String photo, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{photo}/likes").build(photo);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * An individual entry in a profile's feed
     *
     * @param post     Represents the ID of the post object.
     * @param metadata The Graph API supports introspection of objects, which enables you to see all of the connections
     *                 an object has without knowing its type ahead of time.
     */
    @Operation
    public String getPost(String post, @Parameter(optional = true, defaultValue = "0") String metadata) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{post}").build(post);
        WebResource resource = client.resource(uri);
        resource.queryParam("metadata", metadata);

        return resource.get(String.class);
    }

    /**
     * All of the comments on this post
     *
     * @param post   Represents the ID of the post object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getPostComments(String post, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{post}/comments").build(post);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * A status message on a user's wall
     *
     * @param status   Represents the ID of the status object.
     * @param metadata The Graph API supports introspection of objects, which enables you to see all of the connections
     *                 an object has without knowing its type ahead of time.
     */
    @Operation
    public String getStatus(String status, @Parameter(optional = true, defaultValue = "0") String metadata) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{status}").build(status);
        WebResource resource = client.resource(uri);
        resource.queryParam("metadata", metadata);

        return resource.get(String.class);
    }

    /**
     * All of the comments on this message
     *
     * @param status Represents the ID of the status object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getStatusComments(String status, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{status}/comments").build(status);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * A user profile.
     *
     * @param user     Represents the ID of the user object.
     * @param metadata The Graph API supports introspection of objects, which enables you to see all of the connections an
     *                 object has without knowing its type ahead of time.
     */
    @Operation
    public String getUser(String user, @Parameter(optional = true, defaultValue = "0") String metadata) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("metadata", metadata);

        return resource.get(String.class);
    }

    /**
     * Search an individual user's News Feed, restricted to that user's friends
     *
     * @param user     Represents the ID of the user object.
     * @param metadata The Graph API supports introspection of objects, which enables you to see all of the connections an
     *                 object has without knowing its type ahead of time.
     * @param q        The text for which to search.
     */
    @Operation
    public String getUserSearch(String user, @Parameter(optional = true, defaultValue = "0") String metadata, @Parameter(optional = true, defaultValue = "facebook") String q) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/home").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("metadata", metadata);
        resource.queryParam("q", q);

        return resource.get(String.class);
    }

    /**
     * The user's News Feed. Requires the read_stream permission
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserHome(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/home").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The user's wall. Requires the read_stream permission to see non-public posts.
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserWall(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/feed").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The photos, videos, and posts in which this user has been tagged. Requires the user_photo_tags,
     * user_video_tags, friend_photo_tags, or friend_video_tags permissions
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserTagged(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/tagged").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The user's own posts. Requires the read_stream permission to see non-public posts.
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserPosts(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/posts").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The user's profile picture
     *
     * @param user Represents the ID of the user object.
     * @param type One of square (50x50), small (50 pixels wide, variable height), and large (about 200 pixels wide,
     *             variable height)
     */
    @Operation
    public String getUserPicture(String user, @Parameter(optional = true, defaultValue = "small") String type) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/picture").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("type", type);

        return resource.get(String.class);
    }

    /**
     * The user's friends
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserFriends(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/friends").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The activities listed on the user's profile
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserActivities(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/activities").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The music listed on the user's profile
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserCheckins(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/checkins").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The interests listed on the user's profile
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserInterests(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/interests").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The music listed on the user's profile
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserMusic(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/music").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The books listed on the user's profile
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserBooks(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/books").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The movies listed on the user's profile
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserMovies(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/movies").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The television listed on the user's profile
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserTelevision(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/television").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * All the pages this user has liked. Requires the user_likes or friend_likes permission
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserLikes(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/likes").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The photos this user is tagged in. Requires the user_photos or friend_photos permission
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserPhotos(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/photos").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The photo albums this user has created. Requires the user_photos or friend_photos permission
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserAlbums(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/albums").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The videos this user has been tagged in. Requires the user_videos or friend_videos permission.
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserVideos(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/videos").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The groups this user is a member of. Requires the user_groups or friend_groups permission
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserGroups(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/groups").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The user's status updates. Requires the read_stream permission
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserStatuses(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/statuses").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The user's posted links. Requires the read_stream permission
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserLinks(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/links").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The user's notes. Requires the read_stream permission
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserNotes(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/notes").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The events this user is attending. Requires the user_events or friend_events permission
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserEvents(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/events").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The threads in this user's inbox. Requires the read_mailbox permission
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserInbox(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/inbox").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The messages in this user's outbox. Requires the read_mailbox permission
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserOutbox(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/outbox").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The updates in this user's inbox. Requires the read_mailbox permission
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserUpdates(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/updates").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The Facebook pages owned by the current user
     *
     * @param user   Represents the ID of the user object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getUserAccounts(String user, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/accounts").build(user);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * An individual video
     *
     * @param video    Represents the ID of the video object.
     * @param metadata The Graph API supports introspection of objects, which enables you to see all of the connections
     *                 an object has without knowing its type ahead of time.
     */
    @Operation
    public String getVideo(String video, @Parameter(optional = true, defaultValue = "0") String metadata) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{video}").build(video);
        WebResource resource = client.resource(uri);
        resource.queryParam("metadata", metadata);

        return resource.get(String.class);
    }

    /**
     * All of the comments on this video
     *
     * @param video  Represents the ID of the video object.
     * @param since  A unix timestamp or any date accepted by strtotime
     * @param until  A unix timestamp or any date accepted by strtotime
     * @param limit  Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     */
    @Operation
    public String getVideoComments(String video, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{video}/comments").build(video);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * Write to the given profile's feed/wall.
     *
     * @param msg         The message
     * @param picture     If available, a link to the picture included with this post
     * @param link        The link attached to this post
     * @param caption     The caption of the link (appears beneath the link name)
     * @param name        The name of the link
     * @param description A description of the link (appears beneath the link caption)
     */
    @Operation
    public void publishMessage(String profile_id, String msg, @Parameter(optional = true) String picture, @Parameter(optional = true) String link, @Parameter(optional = true) String caption, @Parameter(optional = true) String name, @Parameter(optional = true) String description) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{profile_id}/feed").build(profile_id);
        WebResource resource = client.resource(uri);
        Form form = new Form();
        form.add("access_token", accessToken);
        form.add("message", msg);

        if (picture != null)
            form.add("picture", picture);
        if (link != null)
            form.add("link", link);
        if (caption != null)
            form.add("caption", caption);
        if (name != null)
            form.add("name", name);
        if (description != null)
            form.add("description", description);

        resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(form);

    }

    /**
     * Comment on the given post
     *
     * @param postId   Represents the ID of the post object.
     * @param msg comment on the given post
     */
    @Operation
    public void publishComment(String postId, String msg) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{postId}/comments").build(postId);
        WebResource resource = client.resource(uri);
        Form form = new Form();
        form.add("message", msg);

        resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(form);
    }

    /**
     * Write to the given profile's feed/wall.
     *
     * @param postId   Represents the ID of the post object.
     */
    @Operation
    public void like(String postId) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{postId}/likes").build(postId);
        WebResource resource = client.resource(uri);

        resource.type(MediaType.APPLICATION_FORM_URLENCODED).post();
    }

    /**
     * Write a note on the given profile.
     *
     * @param msg The message
     */
    @Operation
    public void publishNote(String profile_id, String msg, String subject) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{profile_id}/notes").build(profile_id);
        WebResource resource = client.resource(uri);
        Form form = new Form();
        form.add("message", msg);
        form.add("subject", subject);

        resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(form);
    }

    /**
     * Write a note on the given profile.
     *
     * @param msg The message
     */
    @Operation
    public void publishLink(String profile_id, String msg, String link) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{profile_id}/links").build(profile_id);
        WebResource resource = client.resource(uri);
        Form form = new Form();
        form.add("message", msg);
        form.add("link", link);

        resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(form);
    }

    /**
     * Post an event in the given profile.
     */
    @Operation
    public void publishEvent(String profile_id) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{profile_id}/events").build(profile_id);
        WebResource resource = client.resource(uri);

        resource.type(MediaType.APPLICATION_FORM_URLENCODED).post();
    }

    /**
     * Attend the given event.
     */
    @Operation
    public void attendEvent(String eventId) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{eventId}/attending").build(eventId);
        WebResource resource = client.resource(uri);

        resource.type(MediaType.APPLICATION_FORM_URLENCODED).post();
    }

    /**
     * Maybe attend the given event.
     *
     * @param eventId Represents the id of the event object
     */
    @Operation
    public void tentativeEvent(String eventId) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{eventId}/maybe").build(eventId);
        WebResource resource = client.resource(uri);

        resource.type(MediaType.APPLICATION_FORM_URLENCODED).post();
    }

    /**
     * Decline the given event.
     *
     * @param eventId Represents the id of the event object
     */
    @Operation
    public void declineEvent(String eventId) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{eventId}/declined").build(eventId);
        WebResource resource = client.resource(uri);

        resource.type(MediaType.APPLICATION_FORM_URLENCODED).post();
    }

    /**
     * Create an album.
     *
     * @param msg The message
     */
    @Operation
    public void publishAlbum(String profile_id, String msg, String name) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{profile_id}/albums").build(profile_id);
        WebResource resource = client.resource(uri);
        Form form = new Form();
        form.add("message", msg);
        form.add("name", name);

        resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(form);
    }

    /**
     * Upload a photo to an album.
     *
     * @param caption Caption of the photo
     * @param photo File containing the photo
     */
    @Operation
    public void publishPhoto(String albumId, String caption, File photo) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{albumId}/photos").build(albumId);
        WebResource resource = client.resource(uri);

        FormDataMultiPart multiPart = new FormDataMultiPart();
        multiPart.bodyPart(new BodyPart(photo, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        multiPart.field("message", caption);

        resource.type(MediaType.MULTIPART_FORM_DATA).post(multiPart);
    }

    /**
     * Delete an object in the graph.
     *
     * @param objectId The ID of the object to be deleted
     */
    @Operation
    public void deleteObject(String objectId) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{object_id}").build(object_id);
        WebResource resource = client.resource(uri);

        resource.type(MediaType.APPLICATION_FORM_URLENCODED).post();

    }

    /**
     * Remove a 'like' from a post.
     *
     * @param postId The ID of the post to be disliked
     */
    @Operation
    public void dislike(String postId) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{postId}/likes").build(postId);
        WebResource resource = client.resource(uri);

        resource.type(MediaType.APPLICATION_FORM_URLENCODED).post();
    }

    /**
     * A check-in that was made through Facebook Places.
     *
     * @param checkin  Represents the ID of the checkin object.
     * @param metadata The Graph API supports introspection of objects, which enables you to see all of the connections an
     *                 object has without knowing its type ahead of time.
     */
    @Operation
    public String getCheckin(String checkin, @Parameter(optional = true, defaultValue = "0") String metadata) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{checkin}").build(checkin);
        WebResource resource = client.resource(uri);
        resource.queryParam("metadata", metadata);

        return resource.get(String.class);
    }

    /**
     * An application's profile
     *
     * @param application Represents the ID of the application object.
     */
    @Operation
    public String getApplication(String application) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}").build(application);
        WebResource resource = client.resource(uri);

        return resource.get(String.class);
    }

    /**
     * The application's wall.
     *
     * @param application Represents the ID of the application object.
     * @param since       A unix timestamp or any date accepted by strtotime
     * @param until       A unix timestamp or any date accepted by strtotime
     * @param limit       Limit the number of items returned.
     * @param offset      An offset to the response. Useful for paging.
     */
    @Operation
    public String getApplicationWall(String application, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/feed").build(application);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The application's own posts.
     *
     * @param application Represents the ID of the application object.
     * @param since       A unix timestamp or any date accepted by strtotime
     * @param until       A unix timestamp or any date accepted by strtotime
     * @param limit       Limit the number of items returned.
     * @param offset      An offset to the response. Useful for paging.
     */
    @Operation
    public String getApplicationPosts(String application, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/posts").build(application);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The application's logo
     *
     * @param application Represents the ID of the application object.
     * @param type        One of square (50x50), small (50 pixels wide, variable height), and large (about 200 pixels wide,
     *                    variable height)
     */
    @Operation
    public String getApplicationPicture(String application, @Parameter(optional = true, defaultValue = "small") String type) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/picture").build(application);
        WebResource resource = client.resource(uri);
        resource.queryParam("type", type);

        return resource.get(String.class);
    }

    /**
     * The photos, videos, and posts in which this application has been tagged.
     *
     * @param application Represents the ID of the application object.
     * @param since       A unix timestamp or any date accepted by strtotime
     * @param until       A unix timestamp or any date accepted by strtotime
     * @param limit       Limit the number of items returned.
     * @param offset      An offset to the response. Useful for paging.
     */
    @Operation
    public String getApplicationTagged(String application, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/tagged").build(application);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The application's posted links.
     *
     * @param application Represents the ID of the application object.
     * @param since       A unix timestamp or any date accepted by strtotime
     * @param until       A unix timestamp or any date accepted by strtotime
     * @param limit       Limit the number of items returned.
     * @param offset      An offset to the response. Useful for paging.
     */
    @Operation
    public String getApplicationLinks(String application, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/links").build(application);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The photos this application is tagged in.
     *
     * @param application Represents the ID of the application object.
     * @param since       A unix timestamp or any date accepted by strtotime
     * @param until       A unix timestamp or any date accepted by strtotime
     * @param limit       Limit the number of items returned.
     * @param offset      An offset to the response. Useful for paging.
     */
    @Operation
    public String getApplicationPhotos(String application, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/photos").build(application);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The photo albums this application has created.
     *
     * @param application Represents the ID of the application object.
     * @param since       A unix timestamp or any date accepted by strtotime
     * @param until       A unix timestamp or any date accepted by strtotime
     * @param limit       Limit the number of items returned.
     * @param offset      An offset to the response. Useful for paging.
     */
    @Operation
    public String getApplicationAlbums(String application, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/albums").build(application);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The application's status updates.
     *
     * @param application Represents the ID of the application object.
     * @param since       A unix timestamp or any date accepted by strtotime
     * @param until       A unix timestamp or any date accepted by strtotime
     * @param limit       Limit the number of items returned.
     * @param offset      An offset to the response. Useful for paging.
     */
    @Operation
    public String getApplicationStatuses(String application, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/statuses").build(application);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The videos this application has created
     *
     * @param application Represents the ID of the application object.
     * @param since       A unix timestamp or any date accepted by strtotime
     * @param until       A unix timestamp or any date accepted by strtotime
     * @param limit       Limit the number of items returned.
     * @param offset      An offset to the response. Useful for paging.
     */
    @Operation
    public String getApplicationVideos(String application, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/videos").build(application);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The application's notes.
     *
     * @param application Represents the ID of the application object.
     * @param since       A unix timestamp or any date accepted by strtotime
     * @param until       A unix timestamp or any date accepted by strtotime
     * @param limit       Limit the number of items returned.
     * @param offset      An offset to the response. Useful for paging.
     */
    @Operation
    public String getApplicationNotes(String application, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/notes").build(application);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * The events this page is managing
     *
     * @param application Represents the ID of the application object.
     * @param since       A unix timestamp or any date accepted by strtotime
     * @param until       A unix timestamp or any date accepted by strtotime
     * @param limit       Limit the number of items returned.
     * @param offset      An offset to the response. Useful for paging.
     */
    @Operation
    public String getApplicationEvents(String application, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/events").build(application);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

    /**
     * Usage metrics for this application
     *
     * @param application Represents the ID of the application object.
     * @param since       A unix timestamp or any date accepted by strtotime
     * @param until       A unix timestamp or any date accepted by strtotime
     * @param limit       Limit the number of items returned.
     * @param offset      An offset to the response. Useful for paging.
     */
    @Operation
    public String getApplicationInsights(String application, @Parameter(optional = true, defaultValue = "last week") String since, @Parameter(optional = true, defaultValue = "yesterday") String until, @Parameter(optional = true, defaultValue = "3") String limit, @Parameter(optional = true, defaultValue = "2") String offset) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/insights").build(application);
        WebResource resource = client.resource(uri);
        resource.queryParam("since", since);
        resource.queryParam("until", until);
        resource.queryParam("limit", limit);
        resource.queryParam("offset", offset);

        return resource.get(String.class);
    }

}
