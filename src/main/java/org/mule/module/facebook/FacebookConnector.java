/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook;

import com.restfb.DefaultJsonMapper;
import com.restfb.JsonMapper;
import com.restfb.json.JsonObject;
import com.restfb.types.*;
import com.restfb.types.Photo;
import com.restfb.types.Photo.Tag;
import com.restfb.types.Post.Likes;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import com.sun.jersey.multipart.impl.MultiPartWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.mule.api.annotations.ConnectionStrategy;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.oauth.OAuthProtected;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.module.facebook.connection.strategy.FacebookOAuthStrategy;
import org.mule.module.facebook.types.*;
import org.mule.module.facebook.types.Thread;
import org.mule.modules.utils.MuleSoftException;

import javax.imageio.ImageIO;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * Facebook is a social networking service and website launched in February 2004.
 * 
 * @author MuleSoft, inc.
 */
@Connector(name = "facebook", schemaVersion = "2.0", friendlyName="Facebook", minMuleVersion="3.5", configElementName="config-with-oauth")
public class FacebookConnector {

	private static String FACEBOOK_URI = "https://graph.facebook.com/v1.0";
    private static String ACCESS_TOKEN_QUERY_PARAM_NAME = "access_token";
    private static JsonMapper mapper = new DefaultJsonMapper();

    @ConnectionStrategy
    private FacebookOAuthStrategy strategy;

    /**
     * Jersey client
     */
    private Client client;

    /**
     * Constructor
     */
    public FacebookConnector()
    {
    	ClientConfig config = new DefaultClientConfig();
    	config.getClasses().add(MultiPartWriter.class);
        client = Client.create(config);
    }

    /**
     * Gets the user logged details.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:logged-user-details}
     * 
     * @return response from Facebook the actual user.
     */
    @Processor
	@OAuthProtected
    public User loggedUserDetails()
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("me").build();
        return mapper.toJavaObject(this.newWebResource(uri, getStrategy().getAccessToken())
                .type(MediaType.APPLICATION_FORM_URLENCODED).get(String.class), User.class);
    }
    
    /**
     * Search over all public posts in the social graph
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:search-posts}
     * 
     * @param q The search string
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of posts
     */
    @Processor
	@OAuthProtected
    public List<Post> searchPosts(String q,
                                  @Default("last week") String since,
                                  @Default("yesterday") String until,
                                  @Default("100") String limit,
                                  @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("search").build();
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        
        final String jsonResponse = resource.queryParam("q", q)
                                            .queryParam("since", since)
                                            .queryParam("until", until)
                                            .queryParam("limit", limit)
                                            .queryParam("offset", offset)
                                            .queryParam("type", "post")
                                            .get(String.class);
        
        return mapper.toJavaList(jsonResponse, Post.class);
    }
    
    /**
     * Search over all users in the social graph
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:search-users}
     * 
     * @param q The search string
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of users
     */
    @Processor
	@OAuthProtected
    public List<User> searchUsers(String q,
                                  @Default("last week") String since,
                                  @Default("yesterday") String until,
                                  @Default("100") String limit,
                                  @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("search").build();
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        final String jsonResponse = resource.queryParam("q", q)
                                            .queryParam("since", since)
                                            .queryParam("until", until)
                                            .queryParam("limit", limit)
                                            .queryParam("offset", offset)
                                            .queryParam("type", "user")
                                            .get(String.class);
        
        return mapper.toJavaList(jsonResponse, User.class);
    }
    
    /**
     * Search over all pages in the social graph
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:search-pages}
     * 
     * @param q The search string
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of pages
     */
    @Processor
	@OAuthProtected
    public List<Page> searchPages(String q,
                                  @Default("last week") String since,
                                  @Default("yesterday") String until,
                                  @Default("100") String limit,
                                  @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("search").build();
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        final String jsonResponse = resource.queryParam("q", q)
                                            .queryParam("type", "page")
                                            .queryParam("since", since)
                                            .queryParam("until", until)
                                            .queryParam("limit", limit)
                                            .queryParam("offset", offset)
                                            .get(String.class);
        return mapper.toJavaList(jsonResponse, Page.class);
    }
    
    /**
     * Search over all events in the social graph
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:search-events}
     * 
     * @param q The search string
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of events
     */
    @Processor
	@OAuthProtected
    public List<Event> searchEvents(String q,
                                    @Default("last week") String since,
                                    @Default("yesterday") String until,
                                    @Default("100") String limit,
                                    @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("search").build();
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        final String jsonResponse = resource.queryParam("q", q)
                                            .queryParam("type", "event")
                                            .queryParam("since", since)
                                            .queryParam("until", until)
                                            .queryParam("limit", limit)
                                            .queryParam("offset", offset)
                                            .get(String.class);
        return mapper.toJavaList(jsonResponse, Event.class);
    }
    
    /**
     * Search over all groups in the social graph
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:search-groups}
     * 
     * @param q The search string
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of groups
     */
    @Processor
	@OAuthProtected
    public List<Group> searchGroups(String q,
                                    @Default("last week") String since,
                                    @Default("yesterday") String until,
                                    @Default("100") String limit,
                                    @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("search").build();
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        final String jsonResponse = resource.queryParam("q", q)
                                            .queryParam("type", "group")
                                            .queryParam("since", since)
                                            .queryParam("until", until)
                                            .queryParam("limit", limit)
                                            .queryParam("offset", offset)
                                            .get(String.class);
        return mapper.toJavaList(jsonResponse, Group.class);
    }
    
    /**
     * This request returns you or your friend's latest checkins,
     * or checkins where you or your friends have been tagged; currently,
     * it does not accept a query parameter.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:search-checkins}
     * 
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of checkins
     */
    @Processor
	@OAuthProtected
    public List<Checkin> searchCheckins(@Default("last week") String since,
                                        @Default("yesterday") String until,
                                        @Default("100") String limit,
                                        @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("search").build();
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        final String jsonResponse = resource.queryParam("type", "checkin")
                                            .queryParam("since", since)
                                            .queryParam("until", until)
                                            .queryParam("limit", limit)
                                            .queryParam("offset", offset)
                                            .get(String.class);
        return mapper.toJavaList(jsonResponse, Checkin.class);
    }

    /**
     * A photo album
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-album}
     * 
     * @param album Represents the ID of the album object.
     * @param metadata The Graph API supports introspection of objects, which enables
     *            you to see all of the connections an object has without knowing its
     *            type ahead of time.
     * @return The album
     */
    @Processor
	@OAuthProtected
    public Album getAlbum(String album, @Default("0") String metadata)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{album}").build(album);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaObject( resource.queryParam("metadata", metadata).get(String.class), Album.class);
    }

    /**
     * The photos contained in this album
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-album-photos}
     * 
     * @param album Represents the ID of the album object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return response from Facebook
     */
    @Processor
	@OAuthProtected
    public List<Photo> getAlbumPhotos(String album,
                                 @Default("last week") String since,
                                 @Default("yesterday") String until,
                                 @Default("100") String limit,
                                 @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{album}/photos").build(album);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList( resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Photo.class);
    }

    /**
     * The comments made on this album
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-album-comments}
     * 
     * @param album Represents the ID of the album object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return response from Facebook
     */
    @Processor
	@OAuthProtected
    public List<Comment> getAlbumComments(String album,
                                   @Default("last week") String since,
                                   @Default("yesterday") String until,
                                   @Default("100") String limit,
                                   @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{album}/comments").build(album);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList( resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Comment.class);
    }

    /**
     * Specifies information about an event, including the location, event name, and
     * which invitees plan to attend.
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-event}
     * 
     * @param eventId Represents the ID of the event object.
     * @param metadata The Graph API supports introspection of objects, which enables
     *            you to see all of the connections an object has without knowing its
     *            type ahead of time.
     * @return response from Facebook
     */
    @Processor
	@OAuthProtected
    public Event getEvent(String eventId, @Default("0") String metadata)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{event}").build(eventId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaObject( resource.queryParam("metadata", metadata).

        get(String.class), Event.class);
    }

    /**
     * This event's wall
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-event-wall}
     * 
     * @param eventId Represents the ID of the event object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return response from Facebook
     */
    @Processor
	@OAuthProtected
    public List<Post> getEventWall(String eventId,
                               @Default("last week") String since,
                               @Default("yesterday") String until,
                               @Default("100") String limit,
                               @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{event}/feed").build(eventId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList( resource.queryParam("since", since)
                                          .queryParam("until", until)
                                          .queryParam("limit", limit)
                                          .queryParam("offset", offset)
                                          .get(String.class), Post.class);
    }

    /**
     * All of the users who have been not yet responded to their invitation to this
     * event
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-event-no-reply}
     * 
     * @param eventId Represents the ID of the event object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of users
     */
    @Processor
	@OAuthProtected
    public List<User> getEventNoReply(String eventId,
                                  @Default("last week") String since,
                                  @Default("yesterday") String until,
                                  @Default("100") String limit,
                                  @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{event}/noreply").build(eventId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList( resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), User.class);
    }

    /**
     * All of the users who have been responded "Maybe" to their invitation to this
     * event
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-event-maybe}
     * 
     * @param eventId Represents the ID of the event object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of users
     */
    @Processor
	@OAuthProtected
    public List<User> getEventMaybe(String eventId,
                                @Default("last week") String since,
                                @Default("yesterday") String until,
                                @Default("100") String limit,
                                @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{event}/maybe").build(eventId);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
						            .queryParam("since", since)
						            .queryParam("until", until)
						            .queryParam("limit", limit)
						            .queryParam("offset", offset)
						            .get(String.class), User.class);
    }

    /**
     * All of the users who have been invited to this event
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-event-invited}
     * 
     * @param eventId Represents the ID of the event object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of users
     */
    @Processor
	@OAuthProtected
    public List<User> getEventInvited(String eventId,
                                  @Default("last week") String since,
                                  @Default("yesterday") String until,
                                  @Default("100") String limit,
                                  @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{event}/invited").build(eventId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        if (since != null) resource = resource.queryParam("since", since);
        if (until != null) resource = resource.queryParam("until", until);
        if (limit != null) resource = resource.queryParam("limit", limit);
        if (offset != null) resource = resource.queryParam("offset", offset);
        return mapper.toJavaList(resource
            .get(String.class), User.class);
    }

    /**
     * All of the users who are attending this event
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-event-attending}
     * 
     * 
     * @param eventId Represents the ID of the event object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of users
     */
    @Processor
	@OAuthProtected
    public List<User> getEventAttending(String eventId,
                                    @Default("last week") String since,
                                    @Default("yesterday") String until,
                                    @Default("100") String limit,
                                    @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{event}/attending").build(eventId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList(resource
						            .queryParam("since", since)
						            .queryParam("until", until)
						            .queryParam("limit", limit)
						            .queryParam("offset", offset)
						            .get(String.class), User.class);
    }

    /**
     * All of the users who declined their invitation to this event
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-event-declined}
     * 
     * @param eventId Represents the ID of the event object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of events
     */
    @Processor
	@OAuthProtected
    public List<User> getEventDeclined(String eventId,
                                   @Default("last week") String since,
                                   @Default("yesterday") String until,
                                   @Default("100") String limit,
                                   @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{event}/declined").build(eventId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList(resource
						            .queryParam("since", since)
						            .queryParam("until", until)
						            .queryParam("limit", limit)
						            .queryParam("offset", offset)
						            .get(String.class), User.class);
    }

    /**
     * The event's profile picture
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-event-picture}
     * 
     * 
     * @param eventId Represents the ID of the event object.
     * @param type One of square (50x50), small (50 pixels wide, variable height),
     *            and large (about 200 pixels wide, variable height)
     * @return The image as a Byte array
     */
    @Processor
	@OAuthProtected
    public byte[] getEventPicture(String eventId, @Default("small") String type)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{event}/picture").build(eventId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        BufferedImage image = resource.queryParam("type", type).get(BufferedImage.class);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", baos);
        }
        catch (Exception e)
        {
            MuleSoftException.soften(e);
        }
        return baos.toByteArray();
    }
    
    /**
     * Retrieves a list of photos from the event's wall
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-event-photos}
     * 
     * @param eventId The ID of the event
     * @return A list of photos from the event's wall
     */
    @Processor
    @OAuthProtected
    public List<Photo> getEventPhotos(String eventId)
    {
    	URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{eventId}/photos").build(eventId);
    	WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
    	return mapper.toJavaList(resource.get(String.class), Photo.class);
    }

    /**
     * Retrieves a list of videos from the event's wall
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-event-videos}
     * 
     * @param eventId The ID of the event
     * @return A list of videos from the event's wall
     */
    @Processor
    @OAuthProtected
    public List<Video> getEventVideos(String eventId)
    {
    	URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{eventId}/videos").build(eventId);
    	WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
    	return mapper.toJavaList(resource.get(String.class), Video.class);
    }
    
    /**
     * A Facebook group
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-group}
     * 
     * 
     * @param group Represents the ID of the group object.
     * @param metadata The Graph API supports introspection of objects, which enables
     *            you to see all of the connections an object has without knowing its
     *            type ahead of time.
     * @return The group represented by the given id
     */
    @Processor
	@OAuthProtected
    public Group getGroup(String group, @Default("0") String metadata)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{group}").build(group);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaObject( resource.queryParam("metadata", metadata).get(String.class), Group.class);
    }

    /**
     * This group's wall
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-group-wall}
     * 
     * 
     * @param group Represents the ID of the group object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of posts
     */
    @Processor
	@OAuthProtected
    public List<Post> getGroupWall(String group,
                               @Default("last week") String since,
                               @Default("yesterday") String until,
                               @Default("100") String limit,
                               @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{group}/feed").build(group);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList(resource
						            .queryParam("since", since)
						            .queryParam("until", until)
						            .queryParam("limit", limit)
						            .queryParam("offset", offset)
						            .get(String.class), Post.class);
    }

    /**
     * All of the users who are members of this group
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-group-members}
     * 
     * 
     * @param group Represents the ID of the group object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return response from Facebook
     */
    @Processor
	@OAuthProtected
    public List<Member> getGroupMembers(String group,
                                  @Default("last week") String since,
                                  @Default("yesterday") String until,
                                  @Default("100") String limit,
                                  @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{group}/members").build(group);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
							            .queryParam("since", since)
							            .queryParam("until", until)
							            .queryParam("limit", limit)
							            .queryParam("offset", offset)
							            .get(String.class), Member.class);
    }

    /**
     * The profile picture of this group
     * <p/>
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-group-picture}
     * 
     * 
     * @param group Represents the ID of the group object.
     * @param type One of square (50x50), small (50 pixels wide, variable height),
     *            and large (about 200 pixels wide, variable height)
     * @return response from Facebook
     */
    @Processor
	@OAuthProtected
    public byte[] getGroupPicture(String group, @Default("small") String type)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{group}/picture").build(group);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return bufferedImageToByteArray(resource.queryParam("type", type).get(BufferedImage.class));
    }

    /**
     * A link shared on a user's wall 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-link}
     * 
     * 
     * @param link Represents the ID of the link object.
     * @param metadata The Graph API supports introspection of objects, which enables
     *            you to see all of the connections an object has without knowing its
     *            type ahead of time.
     * @return The link from facebook
     */
    @Processor
	@OAuthProtected
    public Link getLink(String link,
                        @Default("0") String metadata)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{link}").build(link);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaObject(resource.queryParam("metadata", metadata).get(String.class), Link.class);
    }

    /**
     * All of the comments on this link 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-link-comments}
     * 
     * 
     * @param link Represents the ID of the link object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of comments
     */
    @Processor
	@OAuthProtected
    public List<Comment> getLinkComments(String link,
                                  @Default("last week") String since,
                                  @Default("yesterday") String until,
                                  @Default("100") String limit,
                                  @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{link}/comments").build(link);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Comment.class);
    }

    /**
     * A Facebook note
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-note}
     * 
     * 
     * @param note Represents the ID of the note object.
     * @param metadata The Graph API supports introspection of objects, which enables
     *            you to see all of the connections an object has without knowing its
     *            type ahead of time.
     * @return The note represented by the given id
     */
    @Processor
	@OAuthProtected
    public Note getNote(String note, @Default("0") String metadata)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{note}").build(note);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaObject( resource.queryParam("metadata", metadata).get(String.class), Note.class);
    }

    /**
     * All of the comments on this note 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-note-comments}
     * 
     * 
     * @param note Represents the ID of the note object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of comments from the given note
     */
    @Processor
	@OAuthProtected
    public List<Comment> getNoteComments(String note,
                                  @Default("last week") String since,
                                  @Default("yesterday") String until,
                                  @Default("100") String limit,
                                  @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{note}/comments").build(note);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList(resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Comment.class);
    }

    /**
     * People who like the note 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-note-likes}
     * 
     * 
     * @param note Represents the ID of the note object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return The links from the given note
     */
    @Processor
	@OAuthProtected
    public Likes getNoteLikes(String note,
                               @Default("last week") String since,
                               @Default("yesterday") String until,
                               @Default("100") String limit,
                               @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{note}/likes").build(note);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaObject( resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Likes.class);
    }

    /**
     * Retrieves the page with the given ID
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-page}
     * 
     * 
     * @param page Represents the ID of the page object.
     * @param metadata The Graph API supports introspection of objects, which enables
     *            you to see all of the connections an object has without knowing its
     *            type ahead of time.
     * @return The page represented by the given id
     */
    @Processor
	@OAuthProtected
    public Page getPage(String page, @Default("0") String metadata)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}").build(page);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaObject( resource.queryParam("metadata", metadata).get(String.class), Page.class);
    }

    /**
     * The page's wall 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-page-wall}
     * 
     * 
     * @param page Represents the ID of the page object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of posts from the given page wall
     */
    @Processor
	@OAuthProtected
    public List<Post> getPageWall(String page,
                              @Default("last week") String since,
                              @Default("yesterday") String until,
                              @Default("100") String limit,
                              @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/feed").build(page);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Post.class);
    }

    /**
     * The page's profile picture 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-page-picture}
     * 
     * 
     * @param page Represents the ID of the page object.
     * @param type One of square (50x50), small (50 pixels wide, variable height),
     *            and large (about 200 pixels wide, variable height)
     * @return A byte array with the page picture
     */
    @Processor
	@OAuthProtected
    public byte[] getPagePicture(String page, @Default("small") String type)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/picture").build(page);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return bufferedImageToByteArray( resource.queryParam("type", type).get(BufferedImage.class));
    }
    
    /**
     * Sets the page picture to the image linked by the URL.
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:set-page-picture-from-link}
     * 
     * @param page The ID of the page which will have its picture set
     * @param imageUrl The web link of the image (e.g. Facebook's logo image URL).
     * @return A boolean result indicating the success or failure of the request
     */
    @Processor
    @OAuthProtected
    public boolean setPagePictureFromLink(String page, String imageUrl) 
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/picture").build(page);
    	WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
    	Form form = new Form();
	    form.add("picture", imageUrl);
	    String res = resource.type(MediaType.MULTIPART_FORM_DATA).post(String.class, form);
	    	
    	return Boolean.parseBoolean(res);
    }
    
    /**
     * Sets the page picture to the image file provided to this message processor 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:set-page-picture-from-source}
     * 
     * @param page The ID of the page which will have its picture set
     * @param source File containing the photo
     * @return A boolean result indicating the success or failure of the request
     */
    @Processor
    @OAuthProtected
    public boolean setPagePictureFromSource(String page, File source) 
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/picture").build(page);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        
        FormDataMultiPart multiPart = new FormDataMultiPart();
        multiPart.bodyPart(new FileDataBodyPart("source", source, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        
        String res = resource.type(MediaType.MULTIPART_FORM_DATA).post(String.class, multiPart);
    	
    	return Boolean.parseBoolean(res);
    }
    
    
    /**
     * The photos, videos, and posts in which this page has been tagged 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-page-tagged}
     * 
     * 
     * @param page Represents the ID of the page object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of posts
     */
    @Processor
	@OAuthProtected
    public List<Post> getPageTagged(String page,
                                @Default("last week") String since,
                                @Default("yesterday") String until,
                                @Default("100") String limit,
                                @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/tagged").build(page);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Post.class);
    }

    /**
     * The page's posted links 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-page-links}
     * 
     * 
     * @param page Represents the ID of the page object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of this page's links
     */
    @Processor
	@OAuthProtected
    public List<Link> getPageLinks(String page,
                               @Default("last week") String since,
                               @Default("yesterday") String until,
                               @Default("100") String limit,
                               @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/links").build(page);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList( resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Link.class);
    }

    /**
     * The photos this page has uploaded 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-page-photos}
     * 
     * 
     * @param page Represents the ID of the page object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of photos from this page
     */
    @Processor
	@OAuthProtected
    public List<Photo> getPagePhotos(
    							String page,
                                @Default("last week") String since,
                                @Default("yesterday") String until,
                                @Default("100") String limit,
                                @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/photos").build(page);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList(resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Photo.class);
    }

    /**
     * The groups this page is a member of 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-page-groups}
     * 
     * 
     * @param page Represents the ID of the page object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return The list of groups
     */
    @Processor
	@OAuthProtected
    public List<Group> getPageGroups(String page,
                                @Default("last week") String since,
                                @Default("yesterday") String until,
                                @Default("100") String limit,
                                @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/groups").build(page);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Group.class);
    }

    /**
     * The photo albums this page has created 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-page-albums}
     * 
     * 
     * @param page Represents the ID of the page object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return The list of albums
     */
    @Processor
	@OAuthProtected
    public List<Album> getPageAlbums(
    							String page,
                                @Default("last week") String since,
                                @Default("yesterday") String until,
                                @Default("100") String limit,
                                @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/albums").build(page);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList(resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Album.class);
    }

    /**
     * The page's status updates 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-page-statuses}
     * 
     * 
     * @param page Represents the ID of the page object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return The list of status messages
     */
    @Processor
	@OAuthProtected
    public List<StatusMessage> getPageStatuses(String page,
                                  @Default("last week") String since,
                                  @Default("yesterday") String until,
                                  @Default("100") String limit,
                                  @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/statuses").build(page);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), StatusMessage.class);
    }

    /**
     * The videos this page has created 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-page-videos}
     * 
     * 
     * @param page Represents the ID of the page object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return The list of videos
     */
    @Processor
	@OAuthProtected
    public List<Video> getPageVideos(String page,
                                @Default("last week") String since,
                                @Default("yesterday") String until,
                                @Default("100") String limit,
                                @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/videos").build(page);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Video.class);
    }

    /**
     * The page's notes 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-page-notes}
     * 
     * 
     * @param page Represents the ID of the page object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return response from Facebook
     */
    @Processor
	@OAuthProtected
    public List<Note> getPageNotes(String page,
                               @Default("last week") String since,
                               @Default("yesterday") String until,
                               @Default("100") String limit,
                               @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/notes").build(page);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Note.class);
    }

    /**
     * The page's own posts 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-page-posts}
     * 
     * 
     * @param page Represents the ID of the page object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of posts
     */
    @Processor
	@OAuthProtected
    public List<Post> getPagePosts(String page,
                               @Default("last week") String since,
                               @Default("yesterday") String until,
                               @Default("100") String limit,
                               @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/posts").build(page);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Post.class);
    }

    /**
     * The events this page is attending 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-page-events}
     * 
     * 
     * @param page Represents the ID of the page object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return The list of events
     */
    @Processor
	@OAuthProtected
    public List<Event> getPageEvents(String page,
                                @Default("last week") String since,
                                @Default("yesterday") String until,
                                @Default("100") String limit,
                                @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/events").build(page);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Event.class);
    }

    /**
     * Checkins made by the friends of the current session user 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-page-checkins}
     * 
     * 
     * @param page Represents the ID of the page object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return response from Facebook
     */
    @Processor
	@OAuthProtected
    public List<Checkin> getPageCheckins(String page,
                                  @Default("last week") String since,
                                  @Default("yesterday") String until,
                                  @Default("100") String limit,
                                  @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{page}/checkins").build(page);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Checkin.class);
    }

    /**
     * An individual photo 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-photo}
     * 
     * 
     * @param photo Represents the ID of the photo object.
     * @param metadata The Graph API supports introspection of objects, which enables
     *            you to see all of the connections an object has without knowing its
     *            type ahead of time.
     * @return The photo represented by the given id
     */
    @Processor
	@OAuthProtected
    public org.mule.module.facebook.types.Photo getPhoto(String photo, @Default("0") String metadata)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{photo}").build(photo);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaObject(resource.queryParam("metadata", metadata).get(String.class), org.mule.module.facebook.types.Photo.class);
    }
    
    /**
     * Tag a photo. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:tag-photo}
     * 
     * @param photoId The ID of the photo where the tag should be placed
     * @param to The userId of the user to tag. One of 'to' or 'tagText' must be set.
     * @param tagText A text string to tag. One of 'to' or 'tagText' must be set.
     * @param x The X coordinate of the tag, as a percentage offset from the left edge of the picture
     * @param y The Y coordinate of the tag, as a percentage offset from the top edge of the picture
     * @return A boolean indicating the success or failure of the request
     */
    @Processor
	@OAuthProtected
    public boolean tagPhoto(String photoId, @Optional String to, @Optional String tagText, @Optional String x, @Optional String y) 
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{photoId}/tags").build(photoId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        
        Form form = new Form();
        if (to != null) { form.add("to", to); }
        if (tagText != null) { form.add("tag_text", tagText); }
        if (x != null) { form.add("x", x); }
        if (y != null) { form.add("y", y); }

        String res = resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(String.class, form);
    	return Boolean.parseBoolean(res);
    }
    
    /**
     * Retrieves the list of tags of a photo
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-photo-tags}
     * 
     * @param photoId The ID of the photo from which tags will be retrieved
     * @return Returns a list of tags found on a photo.
     */
    @Processor
    @OAuthProtected
    public List<Tag> getPhotoTags(String photoId) {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{photoId}/tags").build(photoId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList(resource.get(String.class), Tag.class);
    }
    
    /**
     * All of the comments on this photo 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-photo-comments}
     * 
     * 
     * @param photo Represents the ID of the photo object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return The list of comments of the given photo
     */
    @Processor
	@OAuthProtected
    public List<Comment> getPhotoComments(
    							   String photo,
                                   @Default("last week") String since,
                                   @Default("yesterday") String until,
                                   @Default("100") String limit,
                                   @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{photo}/comments").build(photo);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList(resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Comment.class);
    }

    /**
     * People who like the photo 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-photo-likes}
     * 
     * 
     * @param photo Represents the ID of the photo object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return The likes from the given photo
     */
    @Processor
	@OAuthProtected
    public Likes getPhotoLikes(String photo,
                                @Default("last week") String since,
                                @Default("yesterday") String until,
                                @Default("100") String limit,
                                @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{photo}/likes").build(photo);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaObject( resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Likes.class);
    }

    /**
     * An individual entry in a profile's feed 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-post}
     * 
     * 
     * @param post Represents the ID of the post object.
     * @param metadata The Graph API supports introspection of objects, which enables
     *            you to see all of the connections an object has without knowing its
     *            type ahead of time.
     * @return The post represented by the given id
     */
    @Processor
	@OAuthProtected
    public Post getPost(String post, @Default("0") String metadata)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{post}").build(post);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaObject(resource.queryParam("metadata", metadata).get(String.class), Post.class);
    }

    /**
     * All of the comments on this post 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-post-comments}
     * 
     * 
     * @param post Represents the ID of the post object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of comments from this post
     */
    @Processor
	@OAuthProtected
    public List<Comment> getPostComments(String post,
                                  @Default("last week") String since,
                                  @Default("yesterday") String until,
                                  @Default("100") String limit,
                                  @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{post}/comments").build(post);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList(resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Comment.class);
    }

    /**
     * A status message on a user's wall 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-status}
     * 
     * 
     * @param status Represents the ID of the status object.
     * @param metadata The Graph API supports introspection of objects, which enables
     *            you to see all of the connections an object has without knowing its
     *            type ahead of time.
     * @return The status represented by the given id
     */
    @Processor
	@OAuthProtected
    public StatusMessage getStatus(String status, @Default("0") String metadata)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{status}").build(status);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaObject( resource.queryParam("metadata", metadata).get(String.class), StatusMessage.class);
    }

    /**
     * All of the comments on this message 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-status-comments}
     * 
     * 
     * @param status Represents the ID of the status object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return The list of comments
     */
    @Processor
	@OAuthProtected
    public List<Comment> getStatusComments(String status,
                                    @Default("last week") String since,
                                    @Default("yesterday") String until,
                                    @Default("100") String limit,
                                    @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{status}/comments").build(status);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Comment.class);
    }

    /**
     * A user profile. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param metadata The Graph API supports introspection of objects, which enables
     *            you to see all of the connections an object has without knowing its
     *            type ahead of time.
     * @return The user represented by the given id
     */
    @Processor
	@OAuthProtected
    public User getUser(String user, @Default("0") String metadata)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}").build(user);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaObject( resource.queryParam("metadata", metadata).get(String.class), User.class);
    }

    /**
     * Search an individual user's News Feed, restricted to that user's friends
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-search}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param q The text for which to search.
     * @param metadata The Graph API supports introspection of objects, which enables
     *            you to see all of the connections an object has without knowing its
     *            type ahead of time.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of posts
     */
    @Processor
	@OAuthProtected
    public List<Post> getUserSearch(String user,
                                    @Default("facebook") String q,
                                    @Default("0") String metadata,
                                    @Default("last week") String since,
                                    @Default("yesterday") String until,
                                    @Default("100") String limit,
                                    @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/home").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("q", q)
            .queryParam("metadata", metadata)
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Post.class);
    }

    /**
     * The user's News Feed. Requires the read_stream permission 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-home}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of posts
     */
    @Processor
	@OAuthProtected
    public List<Post> getUserHome(String user,
                              @Default("last week") String since,
                              @Default("yesterday") String until,
                              @Default("100") String limit,
                              @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/home").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Post.class);
    }

    /**
     * The user's wall. Requires the read_stream permission to see non-public posts.
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-wall}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of posts
     */
    @Processor
	@OAuthProtected
    public List<Post> getUserWall(String user,
                              @Default("last week") String since,
                              @Default("yesterday") String until,
                              @Default("100") String limit,
                              @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/feed").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Post.class);
    }

    /**
     * The photos, videos, and posts in which this user has been tagged. Requires the
     * user_photos, user_video_tags, friends_photos, or friend_video_tags permissions
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-tagged}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return response from Facebook
     */
    @Processor
	@OAuthProtected
    public List<Post> getUserTagged(String user,
                                @Default("last week") String since,
                                @Default("yesterday") String until,
                                @Default("100") String limit,
                                @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/tagged").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Post.class);
    }

    /**
     * The user's own posts. Requires the read_stream permission to see non-public
     * posts. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-posts}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of posts
     */
    @Processor
	@OAuthProtected
    public List<Post> getUserPosts(String user,
                               @Default("last week") String since,
                               @Default("yesterday") String until,
                               @Default("100") String limit,
                               @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/posts").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Post.class);
    }

    /**
     * The user's profile picture 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-picture}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param type One of square (50x50), small (50 pixels wide, variable height),
     *            and large (about 200 pixels wide, variable height)
     * @return byte[] with the jpg image
     */
    @Processor
	@OAuthProtected
    public byte[] getUserPicture(String user, @Default("small") String type)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/picture").build(user);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        BufferedImage image = resource.queryParam("type", type).get(BufferedImage.class);
        return bufferedImageToByteArray(image);
    }

    /**
     * The user's friends 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-friends}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of objects with the name and id of the given user's friends
     */
    @Processor
	@OAuthProtected
    public List<NamedFacebookType> getUserFriends(String user,
                                 @Default("last week") String since,
                                 @Default("yesterday") String until,
                                 @Default("100") String limit,
                                 @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/friends").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), NamedFacebookType.class);
    }


    /**
     * The activities listed on the user's profile 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-activities}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of objects containing activity id, name, category and create_time fields. 
     */
    @Processor
	@OAuthProtected
    public List<PageConnection> getUserActivities(String user,
                                    @Default("last week") String since,
                                    @Default("yesterday") String until,
                                    @Default("100") String limit,
                                    @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/activities").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), PageConnection.class);
    }

    /**
     * The music listed on the user's profile 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-checkins}
     *
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list with the user checkins
     */
    @Processor
	@OAuthProtected
    public List<Checkin> getUserCheckins(String user,
                                  @Default("last week") String since,
                                  @Default("yesterday") String until,
                                  @Default("100") String limit,
                                  @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/checkins").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Checkin.class);
    }

    /**
     * The interests listed on the user's profile 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-interests}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list with the user interests
     */
    @Processor
	@OAuthProtected
    public List<PageConnection> getUserInterests(String user,
                                   @Default("last week") String since,
                                   @Default("yesterday") String until,
                                   @Default("100") String limit,
                                   @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/interests").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), PageConnection.class);
    }

    /**
     * The music listed on the user's profile 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-music}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list with the given user's music
     */
    @Processor
	@OAuthProtected
    public List<PageConnection> getUserMusic(String user,
                               @Default("last week") String since,
                               @Default("yesterday") String until,
                               @Default("100") String limit,
                               @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/music").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), PageConnection.class);
    }

    /**
     * The books listed on the user's profile 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-books}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the given user's books
     */
    @Processor
	@OAuthProtected
    public List<PageConnection> getUserBooks(String user,
                               @Default("last week") String since,
                               @Default("yesterday") String until,
                               @Default("100") String limit,
                               @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/books").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), PageConnection.class);
    }

    /**
     * The movies listed on the user's profile 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-movies}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the given user's movies
     */
    @Processor
	@OAuthProtected
    public List<PageConnection> getUserMovies(String user,
                                @Default("last week") String since,
                                @Default("yesterday") String until,
                                @Default("100") String limit,
                                @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/movies").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), PageConnection.class);
    }

    /**
     * The television listed on the user's profile 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-television}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the television listed on the given user's profile
     */
    @Processor
	@OAuthProtected
    public List<PageConnection> getUserTelevision(String user,
                                    @Default("last week") String since,
                                    @Default("yesterday") String until,
                                    @Default("100") String limit,
                                    @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/television").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), PageConnection.class);
    }

    /**
     * All the pages this user has liked. Requires the user_likes or friend_likes
     * permission 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-likes}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing all the pages this user has liked
     */
    @Processor
	@OAuthProtected
    public List<PageConnection> getUserLikes(String user,
                               @Default("last week") String since,
                               @Default("yesterday") String until,
                               @Default("100") String limit,
                               @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/likes").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), PageConnection.class);
    }

    /**
     * The photos this user is tagged in. Requires the user_photos or friend_photos
     * permission 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-photos}
     * 
     *  
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of photos the given user is tagged in
     */
    @Processor
	@OAuthProtected
    public List<Photo> getUserPhotos(String user,
    							@Default("last week") String since,
                                @Default("yesterday") String until,
                                @Default("100") String limit,
                                @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/photos").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
        	.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Photo.class);
    }

    /**
     * Shows all photos that were published to Facebook by this user. Requires the user_photos permission.
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-photos-uploaded}
     *
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of photos the given user has uploaded.
     */
    @Processor
    @OAuthProtected
    public List<Photo> getUserPhotosUploaded(String user,
                                     @Default("last week") String since,
                                     @Default("yesterday") String until,
                                     @Default("100") String limit,
                                     @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/photos/uploaded").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
                .queryParam("since", since)
                .queryParam("until", until)
                .queryParam("limit", limit)
                .queryParam("offset", offset)
                .get(String.class), Photo.class);
    }

    /**
     * The photo albums this user has created. Requires the user_photos or
     * friend_photos permission 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-albums}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the photo albums the given user has created
     */
    @Processor
	@OAuthProtected
    public List<Album> getUserAlbums(
    							String user,
                                @Default("last week") String since,
                                @Default("yesterday") String until,
                                @Default("100") String limit,
                                @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/albums").build(user);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList(resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Album.class);
    }

    /**
     * The videos this user has been tagged in. Requires the user_videos or
     * friend_videos permission. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-videos}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the videos the given user has been tagged in
     */
    @Processor
	@OAuthProtected
    public List<Video> getUserVideos(String user,
                                @Default("last week") String since,
                                @Default("yesterday") String until,
                                @Default("100") String limit,
                                @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/videos").build(user);
        
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Video.class);
    }

    /**
     * Shows all videos that were published to Facebook by this user. Requires the user_videos permission.
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-videos-uploaded}
     *
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the videos uploaded by the given user.
     */
    @Processor
    @OAuthProtected
    public List<Video> getUserVideosUploaded(String user,
                                     @Default("last week") String since,
                                     @Default("yesterday") String until,
                                     @Default("100") String limit,
                                     @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/videos/uploaded").build(user);

        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
                .queryParam("since", since)
                .queryParam("until", until)
                .queryParam("limit", limit)
                .queryParam("offset", offset)
                .get(String.class), Video.class);
    }

    /**
     * The groups this user is a member of. Requires the user_groups or friend_groups
     * permission 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-groups}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the Groups that the given user belongs to
     */
    @Processor
	@OAuthProtected
    public List<Group> getUserGroups(String user,
                                @Default("last week") String since,
                                @Default("yesterday") String until,
                                @Default("100") String limit,
                                @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/groups").build(user);

        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Group.class);
    }

    /**
     * The user's status updates. Requires the read_stream permission 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-statuses}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list contining the user's status updates
     */
    @Processor
	@OAuthProtected
    public List<StatusMessage> getUserStatuses(String user,
                                  @Default("last week") String since,
                                  @Default("yesterday") String until,
                                  @Default("100") String limit,
                                  @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/statuses").build(user);

        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), StatusMessage.class);
    }

    /**
     * The user's posted links. Requires the read_stream permission 
     * {@sample.xml../../../doc/mule-module-facebook.xml.sample facebook:get-user-links}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the given user's posted links 
     */
    @Processor
	@OAuthProtected
    public List<Link> getUserLinks(String user,
                               @Default("last week") String since,
                               @Default("yesterday") String until,
                               @Default("100") String limit,
                               @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/links").build(user);

        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Link.class);
    }

    /**
     * The user's notes. Requires the read_stream permission 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-notes}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the given user's notes
     */
    @Processor
	@OAuthProtected
    public List<Note> getUserNotes(String user,
                               @Default("last week") String since,
                               @Default("yesterday") String until,
                               @Default("100") String limit,
                               @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/notes").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Note.class);
    }

    /**
     * The events this user is attending. Requires the user_events or friend_events
     * permission 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-events}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the events the given user is attending
     */
    @Processor
	@OAuthProtected
    public List<Event> getUserEvents(String user,
                                @Default("last week") String since,
                                @Default("yesterday") String until,
                                @Default("100") String limit,
                                @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/events").build(user);

        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Event.class);
    }

    /**
     * The threads in this user's inbox. Requires the read_mailbox permission
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-inbox}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the threads in the given user's inbox
     */
    @Processor
	@OAuthProtected
    public List<org.mule.module.facebook.types.Thread> getUserInbox(
                               String user,
                               @Default("last week") String since,
                               @Default("yesterday") String until,
                               @Default("100") String limit,
                               @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/inbox").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Thread.class);
    }

    /**
     * The messages in this user's outbox. Requires the read_mailbox permission
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-outbox}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of threads
     */
    @Processor
	@OAuthProtected
    public List<OutboxThread> getUserOutbox(
                                String user,
                                @Default("last week") String since,
                                @Default("yesterday") String until,
                                @Default("100") String limit,
                                @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/outbox").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), OutboxThread.class);
    }

    /**
     * The updates in this user's inbox. Requires the read_mailbox permission
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-updates}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the given user updates
     */
    @Processor
	@OAuthProtected
    public List<OutboxThread> getUserUpdates(String user,
                                 @Default("last week") String since,
                                 @Default("yesterday") String until,
                                 @Default("100") String limit,
                                 @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/updates").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), OutboxThread.class);
    }

    /**
     * The Facebook pages owned by the current user 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-user-accounts}
     * 
     * 
     * @param user Represents the ID of the user object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of objects containing account name, access_token, category, id
     */
    @Processor
	@OAuthProtected
    public List<GetUserAccountResponseType> getUserAccounts(String user,
                                  @Default("last week") String since,
                                  @Default("yesterday") String until,
                                  @Default("100") String limit,
                                  @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{user}/accounts").build(user);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), GetUserAccountResponseType.class);
    }

    /**
     * An individual video 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-video}
     * 
     * 
     * @param video Represents the ID of the video object.
     * @param metadata The Graph API supports introspection of objects, which enables
     *            you to see all of the connections an object has without knowing its
     *            type ahead of time.
     * @return response from Facebook
     */
    @Processor
	@OAuthProtected
    public Video getVideo(String video, @Default("0") String metadata)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{video}").build(video);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaObject(resource
            .queryParam("metadata", metadata)
            .get(String.class), Video.class);
    }

    /**
     * All of the comments on this video 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-video-comments}
     * 
     * 
     * @param video Represents the ID of the video object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the given video's comments
     */
    @Processor
	@OAuthProtected
    public List<Comment> getVideoComments(
    							   String video,
                                   @Default("last week") String since,
                                   @Default("yesterday") String until,
                                   @Default("100") String limit,
                                   @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{video}/comments").build(video);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList(resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Comment.class);
    }

    /**
     * Write to the given profile's feed/wall. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:publish-message}
     * 
     * 
     * @param profile_id the profile where to publish the message
     * @param msg The message
     * @param picture If available, a link to the picture included with this post
     * @param link The link attached to this post
     * @param caption The caption of the link (appears beneath the link name)
     * @param linkName The name of the link
     * @param description A description of the link (appears beneath the link
     *            caption)
     * @param place The page ID of the place that this message is associated with
     * @param tags A list of user IDs of people tagged in this post. You cannot specify
     *             this field without also specifying a place.
     * @return The id of the published object
     */
    @Processor
	@OAuthProtected
    public String publishMessage(
                                 String profile_id,
                                 String msg,
                                 @Optional String picture,
                                 @Optional String link,
                                 @Optional String caption,
                                 @Optional String linkName,
                                 @Optional String description,
                                 @Optional String place,
                                 @Optional List<String> tags)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{profile_id}/feed").build(profile_id);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        Form form = new Form();
        form.add("access_token", getStrategy().getAccessToken());
        form.add("message", msg);

        if (picture != null) form.add("picture", picture);
        if (link != null) form.add("link", link);
        if (caption != null) form.add("caption", caption);
        if (linkName != null) form.add("name", linkName);
        if (description != null) form.add("description", description);
        if (place != null) form.add("place", place);
        if (tags != null) {
            String csvTagsList = StringUtils.join(tags, ",");
            form.add("tags", csvTagsList);
        }

        String json = resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(String.class, form);
		JsonObject obj = mapper.toJavaObject(json, JsonObject.class);
		return obj.getString("id");
    }

    /**
     * Comment on the given post 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:publish-comment}
     * 
     * 
     * @param postId Represents the ID of the post object.
     * @param msg comment on the given post
     * @return The id of the published comment
     */
    @Processor
	@OAuthProtected
    public String publishComment(String postId, String msg)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{postId}/comments").build(postId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        Form form = new Form();
        form.add("access_token", getStrategy().getAccessToken());
        form.add("message", msg);

        WebResource.Builder type = resource.type(MediaType.APPLICATION_FORM_URLENCODED);
        String json = type.accept(MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE)
                        .post(String.class, form);
		JsonObject obj = mapper.toJavaObject(json, JsonObject.class);
		return obj.getString("id");
    }

    /**
     * Write to the given profile's feed/wall. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:like}
     * 
     * 
     * @param postId Represents the ID of the post object.
     * @return Returns true if successfully liked
     */
    @Processor
	@OAuthProtected
    public Boolean like(String postId)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{postId}/likes").build(postId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        String response = resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(String.class);
        
        return Boolean.valueOf(response);
    }

    /**
     * Write a note on the given profile. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:publish-note}
     * 
     * 
     * @param profile_id the profile where to publish the note
     * @param msg The message
     * @param subject the subject of the note
     * @return note id
     */
    @Processor
	@OAuthProtected
    public String publishNote(String profile_id, String msg,
                            String subject)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{profile_id}/notes").build(profile_id);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        Form form = new Form();
        form.add("message", msg);
        form.add("subject", subject);
        WebResource.Builder type = resource
				.type(MediaType.APPLICATION_FORM_URLENCODED);
        String json = type.accept(MediaType.APPLICATION_JSON_TYPE,
				MediaType.APPLICATION_XML_TYPE).post(String.class, form);
        
		JsonObject obj = mapper.toJavaObject(json, JsonObject.class);
        
		return obj.getString("id");
    }

    /**
     * Write a note on the given profile. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:publish-link}
     * 
     * 
     * @param profile_id the profile where to publish the link
     * @param msg The message
     * @param link the link
     * @return link id
     */
    @Processor
	@OAuthProtected
    public String publishLink(String profile_id, String msg, String link)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{profile_id}/links").build(profile_id);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        Form form = new Form();
        form.add("message", msg);
        form.add("link", link);

        WebResource.Builder type = resource
				.type(MediaType.APPLICATION_FORM_URLENCODED);
        String json = type.accept(MediaType.APPLICATION_JSON_TYPE,
				MediaType.APPLICATION_XML_TYPE).post(String.class, form);
        
		JsonObject obj = mapper.toJavaObject(json, JsonObject.class);
		return obj.getString("id");
    }

    /**
     * Post an event in the given profile. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:publish-event}
     * 
     * 
     * @param profile_id the profile where to publish the event
     * @param event_name the name of the event
     * @param start_time the event start time, in ISO-8601 
     * @param end_time the event end time, in ISO-8601
     * @param description the event description
     * @param location the event location
     * @param location_id Facebook Place ID of the place the Event is taking place 
     * @param privacy_type string containing 'OPEN' (default), 'SECRET', or 'FRIENDS' 
     * @return The id of the published event
     */
    @Processor
	@OAuthProtected
    public String publishEvent(String profile_id, String event_name, String start_time, @Optional String end_time, @Optional String description, @Optional String location,
    		@Optional String location_id, @Optional String privacy_type)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{profile_id}/events").build(profile_id);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        
               
        resource = resource.queryParam("access_token", getStrategy().getAccessToken())
        		.queryParam("name", event_name)
        		.queryParam("start_time", start_time);
        
        if(end_time != null) {
        	resource = resource.queryParam("end_time", end_time);
        }
        if(description != null) {
        	resource = resource.queryParam("description", description);
        }
        if(location != null) {
        	resource = resource.queryParam("location", location);
        }
        if(location_id != null) {
        	resource = resource.queryParam("location_id", location_id);
        }
        if(privacy_type != null) {
        	resource = resource.queryParam("privacy_type", privacy_type);
        }

        String json = resource.post(String.class);
        JsonObject response = mapper.toJavaObject(json, JsonObject.class);
        return response.getString("id");
    }

    /**
     * Attend the given event. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:attend-event}
     * 
     * 
     * @param eventId the id of the event to attend
     * @return Boolean result indicating success or failure of operation
     */
    @Processor
	@OAuthProtected
    public Boolean attendEvent(String eventId)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{eventId}/attending").build(eventId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        String res = resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(String.class);

        return Boolean.parseBoolean(res);
    }

    /**
     * Maybe attend the given event. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:tentative-event}
     * 
     * 
     * @param eventId Represents the id of the event object
     * @return The result of the API request
     */
    @Processor
	@OAuthProtected
    public boolean tentativeEvent(String eventId)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{eventId}/maybe").build(eventId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        String res = resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(String.class);

        return Boolean.parseBoolean(res);
    }

    /**
     * Decline the given event. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:decline-event}
     * 
     * 
     * @param eventId Represents the id of the event object
     * @return Boolean result indicating success or failure of operation
     */
    @Processor
	@OAuthProtected
    public Boolean declineEvent(String eventId)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{eventId}/declined").build(eventId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        Form form = new Form();
        form.add("eventId", eventId);
        String res = resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(String.class, form);
        
        return Boolean.parseBoolean(res);
    }
    
    /**
     * Invites a user to a given event.
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:invite-user}
     * 
     * @param eventId The ID of the event.
     * @param userId The ID of the user to invite.
     * @return Boolean result indicating success or failure of operation
     */
    @Processor
	@OAuthProtected
    public Boolean inviteUser(String eventId, String userId)
    {
    	URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{eventId}/invited/{userId}").build(eventId, userId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        Form form = new Form();
        form.add("eventId", eventId);
        form.add("userId", userId);
        String res = resource.type(MediaType.APPLICATION_FORM_URLENCODED).post(String.class, form);
        
        return Boolean.parseBoolean(res);
    }

    /**
     * Uninvites a user from an event.
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:uninvite-user}
     * 
     * @param eventId The ID of the event.
     * @param userId The ID of the user to uninvite
     * @return Boolean result indicating success or failure of operation
     */
    @Processor
    @OAuthProtected
    public Boolean uninviteUser(String eventId, String userId)
    {
    	URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{eventId}/invited/{userId}").build(eventId, userId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        Form form = new Form();
        form.add("eventId", eventId);
        form.add("userId", userId);
        String res = resource.type(MediaType.APPLICATION_FORM_URLENCODED).delete(String.class, form);
        
        return Boolean.parseBoolean(res);
    }
    
    /**
     * Create an album. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:publish-album}
     * 
     * 
     * @param profile_id the id of the profile object
     * @param msg The message
     * @param albumName the name of the album
     * @return The ID of the album that was just created
     */
    @Processor
	@OAuthProtected
    public String publishAlbum(String profile_id, String msg, String albumName)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{profile_id}/albums").build(profile_id);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        Form form = new Form();
        form.add("message", msg);
        form.add("name", albumName);

        String json = resource.type(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE).post(String.class, form);
        
        JsonObject jsonObject = mapper.toJavaObject(json, JsonObject.class);
        String albumId = (String) jsonObject.get("id");
        
        return albumId;
    }

    /**
     * Upload a photo to an album. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:publish-photo}
     * 
     * 
     * @param albumId the id of the album object
     * @param caption Caption of the photo
     * @param photo File containing the photo
     * @return The ID of the photo that was just published
     */
    @Processor
	@OAuthProtected
    public String publishPhoto(String albumId, String caption, File photo)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{albumId}/photos").build(albumId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        FormDataMultiPart multiPart = new FormDataMultiPart();
        multiPart.bodyPart(new FileDataBodyPart("source", photo, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        multiPart.field("message", caption);
        
        String jsonId = resource.type(MediaType.MULTIPART_FORM_DATA).post(String.class, multiPart);
        
        JsonObject obj = mapper.toJavaObject(jsonId, JsonObject.class);
        String photoId = (String) obj.get("id");
        return photoId;
    }
    
    /**
     * Upload a video. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:publish-video}
     * 
     * 
     * @param id The id of the object that this video is being uploaded to (can be user ID, album ID, page ID, etc)
     * @param title Title of the video
     * @param description Description of the video
     * @param video File containing the video
     * @return The ID of the video that was just published
     */
    @Processor
    @OAuthProtected
    public String publishVideo(String id,
    							@Optional String title,
    							@Optional String description,
    							File video)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{id}/videos").build(id);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        FormDataMultiPart multiPart = new FormDataMultiPart();
        multiPart.bodyPart(new FileDataBodyPart("source", video, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        if (title != null) { multiPart.field("title", title); }
        if (description != null) { multiPart.field("description", description); }
            
        String jsonId = resource.type(MediaType.MULTIPART_FORM_DATA).post(String.class, multiPart);
            
        JsonObject obj = mapper.toJavaObject(jsonId, JsonObject.class);
        String videoId = (String) obj.get("id");
        return videoId;
    }

    /**
     * Delete an object in the graph. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:delete-object}
     * 
     * 
     * @param objectId The ID of the object to be deleted
     * @return The result of the deletion
     */
    @Processor
	@OAuthProtected
    public Boolean deleteObject(String objectId)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{object_id}").build(objectId);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        String result = resource.type(MediaType.APPLICATION_FORM_URLENCODED).delete(String.class);
        return Boolean.valueOf(result);
    }

    /**
     * Remove a 'like' from a post. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:dislike}
     * 
     * 
     * @param postId The ID of the post to be disliked
     * @return Returns true if API call was successfull
     */
    @Processor
	@OAuthProtected
    public Boolean dislike(String postId)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{postId}/likes").build(postId);
        WebResource resource = client.resource(uri).queryParam(ACCESS_TOKEN_QUERY_PARAM_NAME, getStrategy().getAccessToken());
        String response = resource.type(MediaType.APPLICATION_FORM_URLENCODED).delete(String.class);
        return Boolean.valueOf(response);
    }

    /**
     * A check-in that was made through Facebook Places. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-checkin}
     * 
     * 
     * @param checkin Represents the ID of the checkin object.
     * @param metadata The Graph API supports introspection of objects, which enables
     *            you to see all of the connections an object has without knowing its
     *            type ahead of time.
     * @return The checkin represented by the given id
     */
    @Processor
	@OAuthProtected
    public Checkin getCheckin(String checkin, @Default("0") String metadata)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{checkin}").build(checkin);
        WebResource resource = client.resource(uri).queryParam(ACCESS_TOKEN_QUERY_PARAM_NAME, getStrategy().getAccessToken());
        return mapper.toJavaObject(resource.queryParam("metadata", metadata).get(String.class), Checkin.class);
    }

    /**
     * An application's profile 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-application}
     * 
     * 
     * @param application Represents the ID of the application object.
     * @return The application represented by the given id
     */
    @Processor
	@OAuthProtected
    public Application getApplication(String application)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}").build(application);
        WebResource resource = client.resource(uri).queryParam(ACCESS_TOKEN_QUERY_PARAM_NAME, getStrategy().getAccessToken());
        return mapper.toJavaObject( resource.get(String.class), Application.class);
    }

    /**
     * The application's wall. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-application-wall}
     * 
     * 
     * @param application Represents the ID of the application object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the given application posts
     */
    @Processor
	@OAuthProtected
    public List<Post> getApplicationWall(String application,
                                     @Default("last week") String since,
                                     @Default("yesterday") String until,
                                     @Default("100") String limit,
                                     @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/feed").build(application);
        WebResource resource = client.resource(uri).queryParam(ACCESS_TOKEN_QUERY_PARAM_NAME, getStrategy().getAccessToken());
        return mapper.toJavaList(resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Post.class);
    }

    /**
     * The application's logo 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-application-picture}
     * 
     * 
     * @param application Represents the ID of the application object.
     * @param type One of square (50x50), small (50 pixels wide, variable height),
     *            and large (about 200 pixels wide, variable height)
     * @return The given application picture
     */
    @Processor
	@OAuthProtected
    public byte[] getApplicationPicture(String application, @Default("small") String type)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/picture").build(application);
        WebResource resource = client.resource(uri).queryParam(ACCESS_TOKEN_QUERY_PARAM_NAME, getStrategy().getAccessToken());
        BufferedImage image = resource.queryParam("type", type).get(BufferedImage.class);
        return bufferedImageToByteArray(image);
    }

    /**
     * The photos, videos, and posts in which this application has been tagged.
     * 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-application-tagged}
     * 
     * 
     * @param application Represents the ID of the application object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return The posts where this application has been tagged
     */
    @Processor
	@OAuthProtected
    public List<GetApplicationTaggedResponseType> getApplicationTagged(String application,
                                       @Default("last week") String since,
                                       @Default("yesterday") String until,
                                       @Default("100") String limit,
                                       @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/tagged").build(application);
        WebResource resource = client.resource(uri).queryParam(ACCESS_TOKEN_QUERY_PARAM_NAME, getStrategy().getAccessToken());
        return mapper.toJavaList(resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), GetApplicationTaggedResponseType.class);
    }

    /**
     * The application's posted links. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-application-links}
     * 
     * 
     * @param application Represents the ID of the application object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containig the links of the given application
     */
    @Processor
	@OAuthProtected
    public List<Post> getApplicationLinks(String application,
                                      @Default("last week") String since,
                                      @Default("yesterday") String until,
                                      @Default("100") String limit,
                                      @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/links").build(application);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList(resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Post.class);
    }

    /**
     * The photos this application is tagged in. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-application-photos}
     *
     * 
     * @param application Represents the ID of the application object.
     * @param since A unix timestamp or any date accepted by shorttime
     * @param until A unix timestamp or any date accepted by shorttime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list with photos
     */
    @Processor
	@OAuthProtected
    public List<Photo> getApplicationPhotos(String application,
                                       @Default("last week") String since,
                                       @Default("yesterday") String until,
                                       @Default("100") String limit,
                                       @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/photos").build(application);
        WebResource resource = client.resource(uri).queryParam(ACCESS_TOKEN_QUERY_PARAM_NAME, getStrategy().getAccessToken());
        return mapper.toJavaList(resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Photo.class);
    }

    /**
     * The photo albums this application has created. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-application-albums}
     * 
     * 
     * @param application Represents the ID of the application object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the given application's albums
     */
    @Processor
	@OAuthProtected
    public List<Album> getApplicationAlbums(String application,
                                       @Default("last week") String since,
                                       @Default("yesterday") String until,
                                       @Default("100") String limit,
                                       @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/albums").build(application);
        WebResource resource = client.resource(uri).queryParam(ACCESS_TOKEN_QUERY_PARAM_NAME, getStrategy().getAccessToken());
        return mapper.toJavaList(resource.queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Album.class);
    }

    /**
     * The application's status updates. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-application-statuses}
     * 
     * 
     * @param application Represents the ID of the application object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the status messages for the given application
     */
    @Processor
	@OAuthProtected
    public List<StatusMessage> getApplicationStatuses(String application,
                                         @Default("last week") String since,
                                         @Default("yesterday") String until,
                                         @Default("100") String limit,
                                         @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/statuses").build(application);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), StatusMessage.class);
    }

    /**
     * The videos this application has created 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-application-videos}
     *
     * 
     * @param application Represents the ID of the application object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list of videos for the given application
     */
    @Processor
	@OAuthProtected
    public List<Video> getApplicationVideos(String application,
                                       @Default("last week") String since,
                                       @Default("yesterday") String until,
                                       @Default("100") String limit,
                                       @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/videos").build(application);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Video.class);
    }

    /**
     * The application's notes. 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-application-notes}
     *
     * 
     * @param application Represents the ID of the application object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the notes for the given application
     */
    @Processor
	@OAuthProtected
    public List<Note> getApplicationNotes(String application,
                                      @Default("last week") String since,
                                      @Default("yesterday") String until,
                                      @Default("100") String limit,
                                      @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/notes").build(application);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Note.class);
    }

    /**
     * The events this page is managing 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-application-events}
     *
     * 
     * @param application Represents the ID of the application object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the events for the given application
     */
    @Processor
	@OAuthProtected
    public List<Event> getApplicationEvents(String application,
                                       @Default("last week") String since,
                                       @Default("yesterday") String until,
                                       @Default("100") String limit,
                                       @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/events").build(application);
        return mapper.toJavaList(this.newWebResource(uri, getStrategy().getAccessToken())
            .queryParam("since", since)
            .queryParam("until", until)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .get(String.class), Event.class);
    }

    /**
     * Usage metrics for this application 
     * 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:get-application-insights}
     *
     * 
     * @param application Represents the ID of the application object.
     * @param since A unix timestamp or any date accepted by strtotime
     * @param until A unix timestamp or any date accepted by strtotime
     * @param limit Limit the number of items returned.
     * @param offset An offset to the response. Useful for paging.
     * @return A list containing the insights for the given application
     */
    @Processor
	@OAuthProtected
    public List<Insight> getApplicationInsights(String application,
                                         @Default("last week") String since,
                                         @Default("yesterday") String until,
                                         @Default("100") String limit,
                                         @Default("0") String offset)
    {
        URI uri = UriBuilder.fromPath(FACEBOOK_URI).path("{application}/insights").build(application);
        WebResource resource = this.newWebResource(uri, getStrategy().getAccessToken());
        return mapper.toJavaList(resource.queryParam("since", since)
							            .queryParam("until", until)
							            .queryParam("limit", limit)
							            .queryParam("offset", offset)
							            .get(String.class), Insight.class);
    }
    
    /**
     * This is a convinience processor that simply fetchs an uri which is expected to return an image
     * and returns it as a Byte array. Notice that I'm using the word image instead of photo or picture which are
     * words with a particular meaning in facebook. By image, I refer to a generic bitmap.
     * 
     * {@sample.xml ../../../doc/mule-module-facebook.xml.sample facebook:download-image}
     * 
     * 
     * @param imageUri the uri of an image resource
     * @return a byte array with the image
     */
    @Processor
	@OAuthProtected
    public byte[] downloadImage(String imageUri) {
    	URI uri = URI.create(imageUri);
    	return this.bufferedImageToByteArray(this.newWebResource(uri, getStrategy().getAccessToken()).get(BufferedImage.class));
    }
    
    private byte[] bufferedImageToByteArray(BufferedImage image)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", baos);
            baos.close();
        }
        catch (IOException e)
        {
            throw MuleSoftException.soften(e);
        }
        catch (IllegalArgumentException iae)
        {
            throw MuleSoftException.soften(iae);
        }
        return baos.toByteArray();
    }
    
    private WebResource newWebResource(URI uri, String accessToken) {
    	return this.client.resource(uri).queryParam(ACCESS_TOKEN_QUERY_PARAM_NAME, accessToken);
    }


    public FacebookOAuthStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(FacebookOAuthStrategy strategy) {
        this.strategy = strategy;
    }

    
    public Client getClient()
    {
        return client;
    }
    
    public void setClient(Client client)
    {
        this.client = client;
    }
}
