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
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mule.module.facebook.types.Photo;

import com.restfb.exception.FacebookJsonMappingException;
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
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * Test Driver for the connector
 */
public class FacebookConnectorUnitTest
{
    private FacebookConnector connector;
    private Client client;
    private WebResource resource;
    
    private static String responseJSON = "{\"id\": \"4\",\"name\": \"Mark Zuckerberg\",\"first_name\": \"Mark\",\"last_name\": \"Zuckerberg\",\"link\": \"https://www.facebook.com/zuck\",\"username\": \"zuck\",\"gender\": \"male\",\"locale\": \"en_US\"}";
    //private static String responseJSONList = "{\"data\": [{\"id\": \"100000604250905_365743746827357\",\"from\": {\"name\": \"Wendell Balahay\",\"id\": \"100000604250905\"},\"story\": \"Wendell Balahay shared Faceboo\u03ba is the only book that we read everyday's photo.\",\"picture\": \"https://fbcdn-photos-a.akamaihd.net/hphotos-ak-prn1/540225_346222975432637_393924030_s.jpg\",\"link\": \"https://www.facebook.com/photo.php?fbid=346222975432637&set=a.176647585723511.46562.176639482390988&type=1\",\"name\": \"Wall Photos\",\"caption\": \"Amazing Watermelon Art\n\nhit [SHARE] if you Like it...)\",\"properties\": [{\"name\": \"By\",\"text\": \"Faceboo\u03ba is the only book that we read everyday\",\"href\": \"https://www.facebook.com/facebok.lovers\"}],\"icon\": \"https://s-static.ak.facebook.com/rsrc.php/v2/yD/r/aS8ecmYRys0.gif\",\"type\": \"photo\",\"object_id\": \"346222975432637\",\"application\": {\"name\": \"Links\",\"id\": \"2309869772\"},\"created_time\": \"2012-07-17T12:54:35+0000\",\"updated_time\": \"2012-07-17T12:54:35+0000\",\"likes\": {\"data\": [{\"name\": \"Junsil Balahay\",\"id\": \"100001404265380\"}],\"count\": 1}}],\"paging\": {\"previous\": \"https://graph.facebook.com/search?q=watermelon&limit=1&type=post&value=1&redirect=1&access_token=AAAAAAITEghMBADoXGyUZCkj16VKqLfUtMRTSYvAtuhYli2R5XAj17Ks02qgqZB0ddvZCz6FQPzuZCAUBuZAmZB6aRd1QheVtJtkYgYzNf3dHhf9NMdaD7S&since=1342529675&__previous=1\",\"next\": \"https://graph.facebook.com/search?q=watermelon&limit=1&type=post&value=1&redirect=1&access_token=AAAAAAITEghMBADoXGyUZCkj16VKqLfUtMRTSYvAtuhYli2R5XAj17Ks02qgqZB0ddvZCz6FQPzuZCAUBuZAmZB6aRd1QheVtJtkYgYzNf3dHhf9NMdaD7S&until=1342529674\"}}";

    @Before
    public void setup()
    {
        connector = new FacebookConnector();
        client = mock(Client.class);
        connector.setClient(client);
        resource = mock(WebResource.class);
        
        when(client.resource((URI) anyObject())).thenReturn(resource);
        when(resource.queryParam(eq(anyString()), "")).thenReturn(resource);
        when(resource.queryParam("type", "user")).thenReturn(resource);
        when(resource.queryParam("type", "event")).thenReturn(resource);
        when(resource.queryParam("type", "checkin")).thenReturn(resource);
        when(resource.get(String.class)).thenReturn(responseJSON);
        when(resource.post(String.class, eq(anyObject()))).thenReturn(responseJSON);
    }

    @Test
    public void testGetAlbum() throws Exception
    {
        connector.getAlbum("test", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test (expected = FacebookJsonMappingException.class)
    public void testGetAlbumPhotos() throws Exception
    {
        connector.getAlbumPhotos("test", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }

    @Test (expected = FacebookJsonMappingException.class)
    public void testSearchPosts()
    {
        connector.searchPosts("", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test (expected = FacebookJsonMappingException.class)
    public void testSearchUsers()
    {
        connector.searchUsers("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test (expected = FacebookJsonMappingException.class)
    public void testSearchCheckinks()
    {
        connector.searchCheckins("", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test (expected = FacebookJsonMappingException.class)
    public void testSearchEvents()
    {
        connector.searchEvents("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetApplication() throws Exception
    {
        final Application res = connector.getApplication("", anyString());
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
        final Link res = connector.getLink("", "", anyString());
        assertNotNull(res);
    }
    
    @Test
    public void testGetNote() throws Exception
    {
        final Note res = connector.getNote("", "", "");
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
        final StatusMessage res = connector.getStatus("", "", "");
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
        final Video res = connector.getVideo("", "", "");
        assertNotNull(res);
    }
    
}