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
import org.mule.module.facebook.types.GetUserAccountResponseType;
import org.mule.module.facebook.types.Member;
import org.mule.module.facebook.types.OutboxThread;
import org.mule.module.facebook.types.Thread;

import com.restfb.types.Album;
import com.restfb.types.Checkin;
import com.restfb.types.Comment;
import com.restfb.types.Event;
import com.restfb.types.Group;
import com.restfb.types.Link;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Note;
import com.restfb.types.Page;
import com.restfb.types.PageConnection;
import com.restfb.types.Photo;
import com.restfb.types.Post;
import com.restfb.types.Post.Likes;
import com.restfb.types.StatusMessage;
import com.restfb.types.User;
import com.restfb.types.Video;

/**
 * Test Driver for the connector
 */
public class FacebookConnectorTestDriver
{
    /**  */
    private static final String ACCESS_TOKEN = System.getenv("facebook-access-token");
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
    public void getPhoto()
    {
        org.mule.module.facebook.types.Photo photo = connector.getPhoto("20531316728", "");
        assertNotNull(photo);
        assertNotNull(photo.getId());
        assertNotNull(photo.getName());
        assertNotNull(photo.getPicture());
        assertNotNull(photo.getLink());
    }
    
    @Test
    public void getPhotoComments()
    {
        List<Comment> comments = connector.getPhotoComments("10151795798083306", "", "", "2", "");
        assertTrue(comments.size() == 2);
        for (Comment comment : comments)
        {
            assertNotNull(comment.getId());
            assertNotNull(comment.getMessage());
        }
    }
    
    @Test
    public void getPhotoLikes()
    {
        Likes likes = connector.getPhotoLikes("10151795798083306", "", "", "2", "");
        assertTrue(likes.getData().size() == 2);
        for (NamedFacebookType like : likes.getData())
        {
            assertNotNull(like.getId());
            assertNotNull(like.getName());
        }
    }
    
    @Test
    public void getPost()
    {
        final Post res = connector.getPost("19292868552_10150189643478553", "");
        assertNotNull(res);
        assertNotNull(res.getFrom().getName());
        assertNotNull(res.getName());
        assertNotNull(res.getProperties().get(0).getName());
    }
    
    @Test
    public void getPostComments()
    {
        List<Comment> comments = connector.getPostComments("10151795798083306", "", "", "2", "");
        assertTrue(comments.size() == 2);
        for (Comment comment : comments)
        {
            assertNotNull(comment.getId());
            assertNotNull(comment.getMessage());
        }
    }
    
    @Test
    public void getStatus()
    {
        StatusMessage status = connector.getStatus(ACCESS_TOKEN, "367501354973", "0");
        assertNotNull(status);
        assertNotNull(status.getFrom().getName());
        assertNotNull(status.getMessage());
    }
    
    @Test
    public void getStatusComments()
    {
        List<Comment> comments = connector.getStatusComments(ACCESS_TOKEN, "367501354973", "", "", "2", "");
        assertTrue(comments.size() == 2);
        for (Comment comment : comments)
        {
            assertNotNull(comment.getId());
            assertNotNull(comment.getMessage());
        }
    }
    
    @Test
    public void getUser()
    {
        User user = connector.getUser("chackn", "0");
        assertNotNull(user);
        assertNotNull(user.getName());
        assertNotNull(user.getLastName());
    }
    
    @Test
    public void getUserSearch()
    {
        List<Post> posts = connector.getUserSearch(ACCESS_TOKEN, "chackn", "a", "", "", "", "2", "");
        assertTrue(posts.size() == 2);
        for (Post post : posts)
        {
            assertNotNull(post.getId());
        }
    }
    
    @Test
    public void getUserHome()
    {
        List<Post> posts = connector.getUserHome(ACCESS_TOKEN, "chackn", "", "", "1", "");
        assertTrue(posts.size() == 1);
        for (Post post : posts)
        {
            assertNotNull(post.getId());
        }
    }
    
    @Test
    public void getUserWall()
    {
        List<Post> posts = connector.getUserWall(ACCESS_TOKEN, "chackn", "", "", "1", "");
        assertTrue(posts.size() == 1);
        for (Post post : posts)
        {
            assertNotNull(post.getId());
        }
    }
    
    @Test
    public void getUserTagged()
    {
        List<Post> posts = connector.getUserTagged(ACCESS_TOKEN, "u2", "", "", "1", "");
        assertNotNull(posts);
    }
    
    @Test
    public void getUserPosts()
    {
        List<Post> posts = connector.getUserPosts(ACCESS_TOKEN, "chackn", "", "", "2", "1");
        assertTrue(posts.size() == 2);
        for (Post post : posts)
        {
            assertNotNull(post.getId());
            assertNotNull(post.getComments());
        }
    }
    
    @Test
    public void getUserPicture() throws Exception
    {
        final Byte[] res = connector.getUserPicture("chackn", "large");
        assertNotNull(res);
    }
    
    @Test
    public void getUserFriends()
    {
        List<NamedFacebookType> users = connector.getUserFriends(ACCESS_TOKEN, "chackn", "", "", "2", "1");
        assertTrue(users.size() == 2);
        for (NamedFacebookType user : users)
        {
            assertNotNull(user.getId());
            assertNotNull(user.getName());
        }
    }
    
    @Test
    public void getUserActivities()
    {
        List<PageConnection> activities = connector.getUserActivities(ACCESS_TOKEN, "chuckn", "", "", "2", "");
        assertNotNull(activities);
    }
    
    @Test
    public void getUserCheckins()
    {
        List<Checkin> checkins = connector.getUserCheckins(ACCESS_TOKEN, "chuckn", "", "", "2", "");
        assertNotNull(checkins);
    }
    
    @Test
    public void getUserInterests()
    {
        List<PageConnection> interests = connector.getUserInterests(ACCESS_TOKEN, "chuckn", "", "", "2", "");
        assertNotNull(interests);
    }
    
    @Test
    public void getUserMusic()
    {
        List<PageConnection> music = connector.getUserMusic(ACCESS_TOKEN, "chuckn", "", "", "2", "");
        assertNotNull(music);
    }
    
    @Test
    public void getUserBooks()
    {
        List<PageConnection> books = connector.getUserBooks(ACCESS_TOKEN, "chuckn", "", "", "2", "");
        assertNotNull(books);
    }
    
    @Test
    public void getUserMovies()
    {
        List<PageConnection> movies = connector.getUserMovies(ACCESS_TOKEN, "chuckn", "", "", "2", "");
        assertNotNull(movies);
    }
    
    @Test
    public void getUserTelevision()
    {
        List<PageConnection> television = connector.getUserTelevision(ACCESS_TOKEN, "chuckn", "", "", "2", "");
        assertNotNull(television);
    }
    
    @Test
    public void getUserLikes()
    {
        List<PageConnection> likes = connector.getUserLikes(ACCESS_TOKEN, "cocacola", "", "", "2", "");
        assertNotNull(likes);
        assertTrue(likes.size() == 2);
        for (PageConnection like : likes)
        {
            assertNotNull(like.getId());
            assertNotNull(like.getCategory());
        }
    }
    
    @Test
    public void getUserPhotos()
    {
        List<Photo> photos = connector.getUserPhotos("cocacola", "", "", "2", "");
        assertNotNull(photos);
        assertTrue(photos.size() == 2);
        for (Photo photo : photos)
        {
            assertNotNull(photo.getId());
            assertNotNull(photo.getFrom());
        }
    }
    
    @Test
    public void getUserAlbums()
    {
        List<Album> albums = connector.getUserAlbums("cocacola", "", "", "2", "");
        assertNotNull(albums);
        assertTrue(albums.size() == 2);
        for (Album album : albums)
        {
            assertNotNull(album.getId());
            assertNotNull(album.getFrom());
        }
    }
    
    @Test
    public void getUserVideos()
    {
        List<Video> videos = connector.getUserVideos(ACCESS_TOKEN, "cocacola", "", "", "2", "");
        assertNotNull(videos);
        assertTrue(videos.size() == 2);
        for (Video video : videos)
        {
            assertNotNull(video.getId());
            assertNotNull(video.getFrom());
        }
    }
    
    @Test
    public void getUserGroups()
    {
        List<Group> groups = connector.getUserGroups(ACCESS_TOKEN, "cocacola", "", "", "2", "");
        assertNotNull(groups);
    }
    
    @Test
    public void getUserStatuses()
    {
        List<StatusMessage> statuses = connector.getUserStatuses(ACCESS_TOKEN, "cocacola", "", "", "2", "");
        assertNotNull(statuses);
        assertTrue(statuses.size() == 2);
        for (StatusMessage status : statuses)
        {
            assertNotNull(status.getId());
            assertNotNull(status.getFrom());
            assertNotNull(status.getMessage());
        }
    }
    
    @Test
    public void getUserLinks()
    {
        List<Link> links = connector.getUserLinks(ACCESS_TOKEN, "cocacola", "", "", "2", "");
        assertNotNull(links);
    }
    
    @Test
    public void getUserNotes()
    {
        List<Note> notes = connector.getUserNotes(ACCESS_TOKEN, "cocacola", "", "", "1", "");
        assertNotNull(notes);
        assertTrue(notes.size() == 1);
        for (Note note : notes)
        {
            assertNotNull(note.getId());
            assertNotNull(note.getFrom());
            assertNotNull(note.getMessage());
        }
    }
    
    @Test
    public void getUserEvents()
    {
        List<Event> events = connector.getUserEvents(ACCESS_TOKEN, "cocacola", "", "", "3", "");
        assertNotNull(events);
        assertTrue(events.size() == 3);
        for (Event event : events)
        {
            assertNotNull(event.getId());
        }
    }
    
    @Test
    @Ignore
    public void getUserInbox()
    {
        List<org.mule.module.facebook.types.Thread> threads = connector.getUserInbox(ACCESS_TOKEN, "chackn", "", "", "3", "");
        assertNotNull(threads);
        assertTrue(threads.size() == 3);
        for (Thread thread : threads)
        {
            assertNotNull(thread.getId());
            assertNotNull(thread.getFrom().getName());
            assertNotNull(thread.getTo().getData().get(0).getName());
        }
    }
    
    @Test
    @Ignore
    public void getUserOutbox()
    {
        List<OutboxThread> threads = connector.getUserOutbox(ACCESS_TOKEN, "chackn", "", "", "3", "");
        assertNotNull(threads);
        assertTrue(threads.size() == 3);
        for (Thread thread : threads)
        {
            assertNotNull(thread.getId());
            assertNotNull(thread.getFrom().getName());
            assertNotNull(thread.getTo().getData().get(0).getName());
        }
    }
    
    @Test
    @Ignore
    public void getUserUpdates()
    {
        List<OutboxThread> updates = connector.getUserUpdates(ACCESS_TOKEN, "chackn", "", "", "3", "");
        assertNotNull(updates);
        assertTrue(updates.size() == 3);
        for (Thread update : updates)
        {
            assertNotNull(update.getId());
            assertNotNull(update.getFrom().getName());
            assertNotNull(update.getTo().getData().get(0).getName());
        }
    }
    
    @Test
    public void getUserAccounts()
    {
        List<GetUserAccountResponseType> accounts = connector.getUserAccounts(ACCESS_TOKEN, "chackn", "", "", "2", "");
        assertNotNull(accounts);
        assertTrue(accounts.size() == 2);
        for (GetUserAccountResponseType account : accounts)
        {
            assertNotNull(account.getId());
        }
    }
    
    @Test
    public void getVideo()
    {
        final Video video = connector.getVideo(ACCESS_TOKEN, "2031763147233", "0");
        assertNotNull(video);
        assertNotNull(video.getFrom().getId());
        assertNotNull(video.getPicture());
    }
    
    @Test
    public void getVideoComments()
    {
        List<Comment> comments = connector.getVideoComments("2031763147233", "", "", "2", "");
        assertNotNull(comments);
        assertTrue(comments.size() == 2);
        for (Comment comment : comments)
        {
            assertNotNull(comment.getId());
        }
    }

    @Test
    @Ignore
    public void testPublishMessage() throws Exception
    {
        final String res = connector.publishMessage(ACCESS_TOKEN, "me", "Hello World!",
            "", "", "", "", "");
        assertNotNull(res);
    }
    
    @Test
    @Ignore
    public void testPublishComment() throws Exception
    {
        final String res = connector.publishComment(ACCESS_TOKEN, "test-post-id", "Hello World!");
        assertNotNull(res);
    }
    
    @Test
    public void like()
    {
        List<Post> posts = connector.getUserPosts(ACCESS_TOKEN, "chackn", "", "", "", "");
        if (!posts.isEmpty())
        {
            final Post post = posts.get(0);
            connector.like(ACCESS_TOKEN, post.getId());
            System.out.println(post.getId());
        }
    }
}
