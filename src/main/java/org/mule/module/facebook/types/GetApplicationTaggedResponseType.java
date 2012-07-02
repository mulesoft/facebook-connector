 package org.mule.module.facebook.types;

import com.restfb.Facebook;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Post.Comments;
 
public class GetApplicationTaggedResponseType extends NamedFacebookType
{
    /**  */
    private static final long serialVersionUID = 1L;
    
    @Facebook
    private NamedFacebookType from;
    
    @Facebook
    private NamedFacebookTypeList to;
    
    @Facebook
    private String message;
    
    @Facebook("created_time")
    private String createdTime;
    
    @Facebook("updated_time")
    private String updatedTime;
    
    @Facebook
    private Comments comments;
    
}

