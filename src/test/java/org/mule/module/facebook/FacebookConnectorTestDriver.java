/**
 * Mule Facebook Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.facebook;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.module.facebook.types.Member;

import com.restfb.types.Album;
import com.restfb.types.Checkin;
import com.restfb.types.Comment;
import com.restfb.types.Event;
import com.restfb.types.Group;
import com.restfb.types.Link;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Note;
import com.restfb.types.Page;
import com.restfb.types.Photo;
import com.restfb.types.Post;
import com.restfb.types.StatusMessage;
import com.restfb.types.User;
import com.restfb.types.Video;
import com.restfb.types.Post.Likes;

/**
 * Test Driver for the connector
 */
public class FacebookConnectorTestDriver
{
    /**  */
    private static final String ACCESS_TOKEN = "AAAAAAITEghMBAAFPC7jTfnOholxNDkNZAOL7mKlurpbZCjZATZB0YI9MSer1RZA0VCDGVLgRlp9VubZAOmk2HBVUt9oAEZClnkECBT58aHE06bQZBqJS12py";
    private FacebookConnector connector;

    @Before
    public void setup()
    {
        connector = new FacebookConnector();
    }
    
    @Test
    public void loggedUserDetails()
    {
        User user = connector.loggedUserDetails(ACCESS_TOKEN);
        assertNotNull(user.getId());
    }
    
    @Test
    public void searchPosts()
    {
        List<Post> posts = connector.searchPosts("chacarita", "", "", "1", "2");
        assertTrue(posts.size() > 0);
        assertNotNull(posts.get(0).getId());
    }
    
    @Test
    public void searchUsers()
    {
        List<User> users = connector.searchUsers(ACCESS_TOKEN, "Norris", "", "", "1", "2");
        assertTrue(users.size() > 0);
        assertNotNull(users.get(0).getId());
    }
    
    @Test
    public void searchPages()
    {
        List<Page> pages = connector.searchPages("Norris", "", "", "2", "1");
        assertTrue(pages.size() == 2);
        assertNotNull(pages.get(0).getId());
        assertNotNull(pages.get(1).getId());
    }
    
    @Test
    public void searchEvents()
    {
        List<Event> events = connector.searchEvents(ACCESS_TOKEN, "facebook", "", "", "2", "");
        assertTrue(events.size() == 2);
        for (Event event : events)
        {
            assertNotNull(event.getId());
            assertNotNull(event.getName());
        }
    }
    
    @Test
    public void searchGroups()
    {
        List<Group> groups = connector.searchGroups(ACCESS_TOKEN, "programming", "", "", "3", "");
        assertTrue(groups.size() == 3);
        for (Group group : groups)
        {
            assertNotNull(group.getId());
            assertNotNull(group.getName());
        }
    }
    
    @Test
    public void searchCheckins()
    {
        List<Checkin> checkins = connector.searchCheckins(ACCESS_TOKEN, "", "", "2", "");
        assertNotNull(checkins);
    }
    
    @Test
    public void getAlbum()
    {
        final Album res = connector.getAlbum("99394368305", "");
        assertNotNull(res.getId());
        assertNotNull(res.getName());
        assertNotNull(res.getCoverPhoto());
        assertNotNull(res.getUpdatedTime());
    }
    
    @Test
    public void getAlbumPhotos()
    {
        List<Photo> photos = connector.getAlbumPhotos("99394368305", "", "", "3", "");
        assertTrue(photos.size() == 3);
        for (Photo photo : photos)
        {
            assertNotNull(photo.getId());
            assertNotNull(photo.getFrom());
            assertNotNull(photo.getPicture());
        }
    }
    
    @Test
    public void getAlbumComments()
    {
        List<Comment> comments = connector.getAlbumComments("99394368305", "", "", "3", "");
        assertTrue(comments.size() == 3);
        for (Comment comment : comments)
        {
            assertNotNull(comment.getId());
            assertNotNull(comment.getFrom());
            assertNotNull(comment.getMessage());
        }
    }
    
    @Test
    public void getEvent()
    {
        final Event event = connector.getEvent("331218348435", "");
        assertNotNull(event);
        assertNotNull(event.getDescription());
        assertNotNull(event.getStartTime());
    }
    
    @Test
    public void getEventWall()
    {
        List<Post> posts = connector.getEventWall(ACCESS_TOKEN, "234960973192305", "", "", "3", "");
        assertTrue(posts.size() == 3);
        for (Post post : posts)
        {
            assertNotNull(post.getId());
            assertNotNull(post.getFrom());
            assertNotNull(post.getMessage());
        }
    }
    
    @Test
    public void getEventNoReply()
    {
        List<User> users = connector.getEventNoReply(ACCESS_TOKEN, "234960973192305", "", "", "2", "");
        assertTrue(users.size() == 2);
        for (User user : users)
        {
            assertNotNull(user.getId());
            assertNotNull(user.getName());
        }
    }
    
    @Test
    public void getEventMaybe()
    {
        List<User> users = connector.getEventMaybe(ACCESS_TOKEN, "234960973192305", "", "", "2", "");
        assertTrue(users.size() == 2);
        for (User user : users)
        {
            assertNotNull(user.getId());
            assertNotNull(user.getName());
        }
    }
    
    @Test
    public void getEventInvited()
    {
        List<User> users = connector.getEventInvited(ACCESS_TOKEN, "234960973192305", "", "", "2", "");
        assertTrue(users.size() == 2);
        for (User user : users)
        {
            assertNotNull(user.getId());
            assertNotNull(user.getName());
        }
    }
    
    @Test
    public void getEventAttending()
    {
        List<User> users = connector.getEventAttending(ACCESS_TOKEN, "234960973192305", "", "", "2", "");
        assertTrue(users.size() == 2);
        for (User user : users)
        {
            assertNotNull(user.getId());
            assertNotNull(user.getName());
        }
    }
    
    @Test
    public void getEventPicture() throws Exception
    {
        final Byte[] res = connector.getEventPicture("234960973192305", "small");
        assertNotNull(res);
    }
    
    @Test
    public void getGroup() throws Exception
    {
        final Group res = connector.getGroup("195466193802264", "");
        assertNotNull(res.getId());
        assertNotNull(res.getDescription());
        assertNotNull(res.getOwner().getName());
    }
    
    @Test
    public void getGroupWall()
    {
        List<Post> posts = connector.getGroupWall(ACCESS_TOKEN, "18708376680", "", "", "", "");
        assertTrue(posts.size() > 0);
        for (Post post : posts)
        {
            assertNotNull(post.getId());
            assertNotNull(post.getFrom().getName());
        }
    }
    
    @Test
    public void getGroupMembers()
    {
        List<Member> members = connector.getGroupMembers(ACCESS_TOKEN, "18708376680", "", "", "", "");
        assertTrue(members.size() > 0);
        for (Member memeber : members)
        {
            assertNotNull(memeber.getId());
            assertNotNull(memeber.getName());
            assertNotNull(memeber.getAdministrator());
        }
    }
    
    @Test
    public void getGroupPicture() throws Exception
    {
        final Byte[] res = connector.getGroupPicture("18708376680", "small");
        assertNotNull(res);
    }
    
    @Test
    public void getLink()
    {
        final Link res = connector.getLink(ACCESS_TOKEN, "114961875194024", "");
        assertNotNull(res.getId());
        assertNotNull(res.getFrom().getId());
    }
    
    @Test
    public void getLinkComments()
    {
        List<Comment> comments = connector.getLinkComments(ACCESS_TOKEN, "114961875194024", "", "", "1", "");
        assertTrue(comments.size() == 1);
        for (Comment comment : comments)
        {
            assertNotNull(comment.getId());
        }
    }
    
    @Test
    public void getNote()
    {
        final Note note = connector.getNote(ACCESS_TOKEN, "122788341354", "");
        assertNotNull(note);
        assertNotNull(note.getId());
        assertNotNull(note.getFrom().getName());
        assertNotNull(note.getMessage());
    }
    
    @Test
    public void getNoteComments()
    {
        List<Comment> comments = connector.getNoteComments("122788341354", "", "", "1", "");
        assertTrue(comments.size() == 1);
        for (Comment comment : comments)
        {
            assertNotNull(comment.getId());
        }
    }
    
    @Test
    public void getNoteLikes()
    {
        Likes likes = connector.getNoteLikes("122788341354", "", "", "2", "");
        assertTrue(likes.getData().size() == 2);
        for (NamedFacebookType like : likes.getData())
        {
            assertNotNull(like.getId());
            assertNotNull(like.getName());
        }
    }
    
    @Test
    public void getPage()
    {
        final Page page = connector.getPage("cocacola", "");
        assertNotNull(page);
        assertNotNull(page.getId());
        assertNotNull(page.getDescription());
        assertNotNull(page.getUsername());
    }
    
    @Test
    public void getPageWall()
    {
        List<Post> posts = connector.getPageWall(ACCESS_TOKEN, "cocacola", "", "", "2", "");
        assertTrue(posts.size() == 2);
        for (Post post : posts)
        {
            assertNotNull(post.getId());
            assertNotNull(post.getFrom().getName());
            assertNotNull(post.getTo().get(0).getName());
        }
    }
    
    @Test
    public void getPagePicture() throws Exception
    {
        final Byte[] res = connector.getPagePicture("cocacola", "large");
        assertNotNull(res);
    }
    
    @Test
    public void getPageTagged()
    {
        List<Post> posts = connector.getPageTagged(ACCESS_TOKEN, "cocacola", "", "", "2", "");
        assertTrue(posts.size() == 2);
        for (Post post : posts)
        {
            assertNotNull(post.getId());
            assertNotNull(post.getFrom().getName());
            assertNotNull(post.getTo().get(0).getName());
        }
    }
    
    @Test
    public void getPagePhotos()
    {
        List<Photo> photos = connector.getPagePhotos("cocacola", "", "", "2", "");
        assertTrue(photos.size() == 2);
        for (Photo photo : photos)
        {
            assertNotNull(photo.getId());
            assertNotNull(photo.getFrom().getName());
            assertNotNull(photo.getImages().get(0).getSource());
        }
    }
    
    @Test
    public void getPageGroups()
    {
        List<Group> groups = connector.getPageGroups(ACCESS_TOKEN, "cocacola", "", "", "2", "");
        assertNotNull(groups);
    }
    
    @Test
    public void getPageAlbums()
    {
        List<Album> albums = connector.getPageAlbums("cocacola", "", "", "2", "");
        assertTrue(albums.size() == 2);
        for (Album album : albums)
        {
            assertNotNull(album.getId());
            assertNotNull(album.getFrom().getName());
            assertNotNull(album.getCreatedTime());
        }
    }
    
    @Test
    public void getPageStatuses()
    {
        List<StatusMessage> statuses = connector.getPageStatuses(ACCESS_TOKEN, "cocacola", "", "", "2", "");
        assertTrue(statuses.size() == 2);
        for (StatusMessage status : statuses)
        {
            assertNotNull(status.getId());
            assertNotNull(status.getFrom().getName());
        }
    }
    
    @Test
    public void getPageVideos()
    {
        List<Video> videos = connector.getPageVideos(ACCESS_TOKEN, "cocacola", "", "", "2", "");
        assertTrue(videos.size() == 2);
        for (Video video : videos)
        {
            assertNotNull(video.getId());
            assertNotNull(video.getFrom().getName());
            assertNotNull(video.getComments());
        }
    }
    
    @Test
    public void getPageNotes()
    {
        List<Note> notes = connector.getPageNotes(ACCESS_TOKEN, "cocacola", "", "", "1", "");
        assertTrue(notes.size() == 1);
        for (Note note : notes)
        {
            assertNotNull(note.getId());
            assertNotNull(note.getFrom().getName());
            assertNotNull(note.getSubject());
        }
    }
    
    @Test
    public void getPagePosts()
    {
        List<Post> posts = connector.getPagePosts(ACCESS_TOKEN, "facebook", "", "", "3", "");
        assertTrue(posts.size() == 3);
        for (Post post : posts)
        {
            assertNotNull(post.getId());
            assertNotNull(post.getFrom().getName());
            assertNotNull(post.getLikes().getCount());
        }
    }
    
    @Test
    public void getPageEvents()
    {
        List<Event> events = connector.getPageEvents(ACCESS_TOKEN, "cocacola", "", "", "2", "");
        assertTrue(events.size() == 2);
        for (Event event : events)
        {
            assertNotNull(event.getId());
            assertNotNull(event.getStartTime());
            assertNotNull(event.getEndTime());
        }
    }
    
    @Test
    public void getPageCheckins()
    {
        List<Checkin> checkins = connector.getPageCheckins(ACCESS_TOKEN, "cocacola", "", "", "2", "");
        assertNotNull(checkins);
    }
    
    @Test
    public void getUserPicture() throws Exception
    {
        final Byte[] res = connector.getUserPicture("chackn", "large");
        assertNotNull(res);
    }


    @Test
    @Ignore
    public void testPublishMessage() throws Exception
    {
        final String res = connector.publishMessage(ACCESS_TOKEN, "me", "testFacebookConnector6",
            "", "", "", "", "");
        assertNotNull(res);
    }
    
    @Test
    public void getStatus()
    {
        assertNotNull(connector.getStatus(ACCESS_TOKEN, "367501354973", "0"));
    }
    
    @Test
    public void getVideo()
    {
        assertNotNull(connector.getVideo(ACCESS_TOKEN, "817129783203", "0"));
    }
}
