 package org.mule.module.facebook.types;

import java.io.Serializable;
import java.util.Date;

import com.restfb.Facebook;
import com.restfb.types.FacebookType;
import com.restfb.types.FacebookType.Metadata;
import com.restfb.types.NamedFacebookType;
 
 
public class Thread extends FacebookType
{
    /**  */
    private static final long serialVersionUID = 1L;

    @Facebook
    private NamedFacebookType from;
    
    @Facebook
    private NamedFacebookTypeList to;
    
    @Facebook("updated_time")
    private Date updatedTime;
    
    @Facebook
    private Integer unread;
    
    @Facebook
    private Integer unseen;
    
    @Facebook
    private CommentList comments;
}

