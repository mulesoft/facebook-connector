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

import org.junit.Test;
import org.mockito.Mockito;

public class FacebookConnectorListsUnitTest extends FacebookConnectorGenericUnitTest
{
    public FacebookConnectorListsUnitTest()
    {
        super("{\"data\":[{\"id\":\"769398592_10151145541863593\",\"from\":{\"name\":\"Bernardo Jeannot\",\"id\":\"769398592\"},\"message\":\"PRIMERA RONDA\\n1. Temperley (V)\\n2. San Carlos (L)\\n3. Almagro (V)\\n4. Central C\u00f3rdoba (L)\\n5. Atlanta (V)\\n6. Platense (L)\\n7. Comunicaciones (V)\\n8. Trist\u00e1n Su\u00e1rez (L)\\n9. Mor\u00f3n (V)\\n10. Acassuso (L)\\n11. Los Andes (V)\\n12. San Telmo (L)\\n13. Estudiantes (V)\\n14. Villa D\u00e1lmine (L)\\n15. Defensores de Belgrano (V)\\n16. Chacarita (L)\\n17. Barracas Central (V)\\n18. Brown de Adrogu\u00e9 (L)\\n19. Libre\\n20. Colegiales (V)\\n21. Armenio (L)\",\"type\":\"status\",\"created_time\":\"2012-07-24T12:43:23+0000\",\"updated_time\":\"2012-07-24T12:43:23+0000\"}],\"paging\":{\"previous\":\"https:\\/\\/graph.facebook.com\\/search?q=chacarita&limit=1&since=1343133803&type=post&value=1&__previous=1\",\"next\":\"https:\\/\\/graph.facebook.com\\/search?q=chacarita&limit=1&type=post&value=1&until=1343133802\"}}");
    }
    
    @Test
    public void testSearchPosts()
    {
        connector.searchPosts("", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetAlbumPhotos()
    {
        connector.getAlbumPhotos("test", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testSearchUsers()
    {
        connector.searchUsers("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testSearchCheckinks()
    {
        connector.searchCheckins("", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testSearchEvents()
    {
        connector.searchEvents("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testSearchPages()
    {
        connector.searchPages("", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testSearchGroups()
    {
        connector.searchGroups("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetAlbumComments()
    {
        connector.getAlbumComments("", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetEventWall()
    {
        connector.getEventWall("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetEventNoReply()
    {
        connector.getEventNoReply("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetEventMaybe()
    {
        connector.getEventMaybe("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetEventInvited()
    {
        connector.getEventInvited("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetEventAttending()
    {
        connector.getEventAttending("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetEventDeclined()
    {
        connector.getEventDeclined("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetGroupWall()
    {
        connector.getGroupWall("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetGroupMembers()
    {
        connector.getGroupMembers("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetLinkComments()
    {
        connector.getLinkComments("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetNoteComments()
    {
        connector.getNoteComments("", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetPageWall()
    {
        connector.getPageWall("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetPageTagged()
    {
        connector.getPageTagged("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetPageLinks()
    {
        connector.getPageLinks("", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetPagePhotos()
    {
        connector.getPagePhotos("", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetPageGroups()
    {
        connector.getPageGroups("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetPageAlbums()
    {
        connector.getPageAlbums("", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetPageStatuses()
    {
        connector.getPageStatuses("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetPageVideos()
    {
        connector.getPageVideos("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetPageNotes()
    {
        connector.getPageNotes("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetPagePosts()
    {
        connector.getPagePosts("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetPageEvents()
    {
        connector.getPageEvents("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetPageCheckins()
    {
        connector.getPageCheckins("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetPhotoComments()
    {
        connector.getPhotoComments("", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetPostComments()
    {
        connector.getPostComments("", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetStatusComments()
    {
        connector.getStatusComments("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetUserSearch()
    {
        connector.getUserSearch("", "", "", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetUserHome()
    {
        connector.getUserHome("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetUserWall()
    {
        connector.getUserWall("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetUserTagged()
    {
        connector.getUserTagged("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }
    
    @Test
    public void testGetUserPosts()
    {
        connector.getUserPosts("", "", "", "", "", "");
        Mockito.verify(resource).get(String.class);
    }

}