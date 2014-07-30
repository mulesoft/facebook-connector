/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

 package org.mule.modules.types;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mule.module.facebook.types.GetApplicationTaggedResponseType;
import org.mule.module.facebook.types.NamedFacebookTypeList;

import com.restfb.types.NamedFacebookType;
import com.restfb.types.Post.Comments;
 
public class GetApplicationTaggedResponseTypeUnitTest
{
    @Test
    public void testSettersAndGetters()
    {
        GetApplicationTaggedResponseType responseType = new GetApplicationTaggedResponseType(
            mock(NamedFacebookType.class), mock(NamedFacebookTypeList.class), "", "", "",
            mock(Comments.class));
        assertNotNull(responseType.getFrom());
        assertNotNull(responseType.getTo());
        assertNotNull(responseType.getCreatedTime());
        assertNotNull(responseType.getUpdatedTime());
        assertNotNull(responseType.getMessage());
        assertNotNull(responseType.getComments());
    }
}

