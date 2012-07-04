 package org.mule.module.facebook.types;

import com.restfb.Facebook;
import com.restfb.types.CategorizedFacebookType;
 
public class GetUserAccountResponseType extends CategorizedFacebookType
{
    /**  */
    private static final long serialVersionUID = 1L;
    
    @Facebook("access_token")
    private String accessToken;

    
    public String getAccessToken()
    {
        return accessToken;
    }
}