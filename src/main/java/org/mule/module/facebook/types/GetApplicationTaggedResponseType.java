/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

 package org.mule.module.facebook.types;

import com.restfb.Facebook;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Post.Comments;
 
public class GetApplicationTaggedResponseType extends NamedFacebookType
{
    /**  */
    private static final long serialVersionUID = 1L;
    
    /**
     * Default constructor
     * */
    public GetApplicationTaggedResponseType()
    {
        super();
    }
    
    /**
     * Constructor for testing purposes
     * */
    public GetApplicationTaggedResponseType(final NamedFacebookType from, final NamedFacebookTypeList to,
                                            final String message, final String createdTime,
                                            final String updatedTime, final Comments comments)
    {
        this.from = from;
        this.to = to;
        this.message = message;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.comments = comments;
    }
    
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