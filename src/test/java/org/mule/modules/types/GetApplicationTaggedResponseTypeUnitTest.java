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

