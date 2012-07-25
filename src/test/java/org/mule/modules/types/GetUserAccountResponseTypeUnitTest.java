 package org.mule.modules.types;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mule.module.facebook.types.GetUserAccountResponseType;
 
public class GetUserAccountResponseTypeUnitTest
{
    private GetUserAccountResponseType responseType;
    
    @Before
    public void setup()
    {
        responseType = new GetUserAccountResponseType();
    }
    
    @Test
    public void testGettersAnsSetters()
    {
        final String token = "token";
        responseType.setAccessToken(token);
        assertEquals(responseType.getAccessToken(), token);
    }
}

