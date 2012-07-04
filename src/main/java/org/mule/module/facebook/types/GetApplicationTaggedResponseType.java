/**
 * Mule Facebook Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

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

    public NamedFacebookType getFrom()
    {
        return from;
    }
    
    public NamedFacebookTypeList getTo()
    {
        return to;
    }

    public String getMessage()
    {
        return message;
    }

    public String getCreatedTime()
    {
        return createdTime;
    }

    public String getUpdatedTime()
    {
        return updatedTime;
    }

    public Comments getComments()
    {
        return comments;
    }

}