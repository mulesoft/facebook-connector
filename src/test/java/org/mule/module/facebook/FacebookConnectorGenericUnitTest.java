/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.mule.module.facebook.connection.strategy.FacebookOAuthStrategy;
import org.springframework.core.io.ClassPathResource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
 
public abstract class FacebookConnectorGenericUnitTest
{
    public FacebookConnector connector;
    public Client client;
    public WebResource resource;
    public String jsonResponse;

    public FacebookConnectorGenericUnitTest(final String json)
    {
        jsonResponse = json;
    }
    
    @Before
    public void setup() throws IOException
    {
        connector = new FacebookConnector();
        FacebookOAuthStrategy facebookOAuthStrategy = new FacebookOAuthStrategy();
        facebookOAuthStrategy.setAccessToken("ACCESS_TOKEN");
        connector.setStrategy(facebookOAuthStrategy);
        client = mock(Client.class);
        connector.setClient(client);
        resource = mock(WebResource.class);
        final BufferedImage image = ImageIO.read(new ClassPathResource("image.jpg").getInputStream());
        
        when(client.resource((URI) anyObject())).thenReturn(resource);
        when(resource.queryParam(anyString(), anyString())).thenReturn(resource);
        when(resource.queryParam("type", "user")).thenReturn(resource);
        when(resource.queryParam("type", "event")).thenReturn(resource);
        when(resource.queryParam("type", "checkin")).thenReturn(resource);
        when(resource.queryParam("type", "post")).thenReturn(resource);
        when(resource.queryParam("type", "page")).thenReturn(resource);
        when(resource.queryParam("type", "group")).thenReturn(resource);
        when(resource.get(String.class)).thenReturn(jsonResponse);
        when(resource.get(BufferedImage.class)).thenReturn(image);
        when(resource.post(String.class, eq(anyObject()))).thenReturn(jsonResponse);
        
    }
}

