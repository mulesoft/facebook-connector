/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;

import java.awt.image.BufferedImage;

import org.junit.Test;
import org.mockito.Mockito;
import org.mule.module.facebook.types.Photo;

import com.restfb.types.Application;
import com.restfb.types.Event;
import com.restfb.types.Group;
import com.restfb.types.Link;
import com.restfb.types.Note;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.StatusMessage;
import com.restfb.types.User;
import com.restfb.types.Video;

/**
 * Test Driver for the connector
 */
public class FacebookConnectorUnitTest extends FacebookConnectorGenericUnitTest
{
    
    public FacebookConnectorUnitTest()
    {
        super("{\"id\": \"4\",\"name\": \"Mark Zuckerberg\",\"first_name\": \"Mark\",\"last_name\": \"Zuckerberg\",\"link\": \"https://www.facebook.com/zuck\",\"username\": \"zuck\",\"gender\": \"male\",\"locale\": \"en_US\"}");
    }

    @Test
    public void testGetAlbum() throws Exception
    {
        connector.getAlbum("test", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetApplication() throws Exception
    {
        final Application res = connector.getApplication(anyString());
        assertNotNull(res);
    }
    
    @Test
    public void testGetEvent() throws Exception
    {
        final Event res = connector.getEvent("", "");
        assertNotNull(res);
    }
    
    @Test
    public void testGetGroup() throws Exception
    {
        final Group res = connector.getGroup("", "");
        assertNotNull(res);
    }
    
    @Test
    public void testGetLink() throws Exception
    {
        final Link res = connector.getLink("", anyString());
        assertNotNull(res);
    }
    
    @Test
    public void testGetNote() throws Exception
    {
        final Note res = connector.getNote("", "");
        assertNotNull(res);
    }
    
    @Test
    public void testGetPage() throws Exception
    {
        final Page res = connector.getPage("", "");
        assertNotNull(res);
    }
    
    @Test
    public void testGetPhoto() throws Exception
    {
        final Photo res = connector.getPhoto("", "");
        assertNotNull(res);
    }
    
    @Test
    public void testGetPost() throws Exception
    {
        final Post res = connector.getPost("", "");
        assertNotNull(res);
    }
    
    @Test
    public void testGetStatus() throws Exception
    {
        final StatusMessage res = connector.getStatus("", "");
        assertNotNull(res);
    }
    
    @Test
    public void testGetUser() throws Exception
    {
        final User res = connector.getUser("", "");
        assertNotNull(res);
    }
    
    @Test
    public void testGetVideo() throws Exception
    {
        final Video res = connector.getVideo("", "");
        assertNotNull(res);
    }
    
    @Test
    public void testGetEventPicture()
    {
        connector.getEventPicture("", "");
        Mockito.verify(resource).get(BufferedImage.class);
    }
    
    @Test
    public void testGetGroupPicture()
    {
        connector.getGroupPicture("", "");
        Mockito.verify(resource).get(BufferedImage.class);
    }
    
    @Test
    public void testGetNoteLikes()
    {
        connector.getNoteLikes("", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testFailGetEventPicture()
    {
        Mockito.when(resource.get(BufferedImage.class)).thenReturn(null);
        connector.getEventPicture("", "");
        Mockito.verify(resource).get(BufferedImage.class);
    }
    
    @Test
    public void testGetPagePicture()
    {
        connector.getPagePicture("", "");
        Mockito.verify(resource).get(BufferedImage.class);
    }
    
    @Test
    public void testGetPhotoLikes()
    {
        connector.getPhotoLikes("", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetUserPicture()
    {
        connector.getUserPicture("", "");
        Mockito.verify(resource).get(BufferedImage.class);
    }
    
    @Test
    public void testGetCheckin()
    {
        connector.getCheckin("", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetApplicationPicture()
    {
        connector.getApplicationPicture("", "");
        Mockito.verify(resource).get(BufferedImage.class);
    }
    
    @Test
    public void testFailGetApplicationPicture()
    {
        Mockito.when(resource.get(BufferedImage.class)).thenReturn(null);
        connector.getEventPicture("", "");
        Mockito.verify(resource).get(BufferedImage.class);
    }
    
}