 package org.mule.module.facebook.types;

import java.util.Date;

import com.restfb.Facebook;
import com.restfb.types.FacebookType;
import com.restfb.types.NamedFacebookType;
import com.restfb.util.DateUtils;
 
 
public class Thread extends FacebookType
{
    /**  */
    private static final long serialVersionUID = 1L;

    @Facebook
    private NamedFacebookType from;
    
    @Facebook
    private NamedFacebookTypeList to;
    
    @Facebook("updated_time")
    private String updatedTime;
    
    @Facebook
    private Integer unread;
    
    @Facebook
    private Integer unseen;
    
    @Facebook
    private CommentList comments;
    
    public NamedFacebookType getFrom()
    {
        return from;
    }
    
    public NamedFacebookTypeList getTo()
    {
        return to;
    }
    
    public Date getUpdatedTime()
    {
        return DateUtils.toDateFromLongFormat(updatedTime);
    }
    
    public Integer getUnread()
    {
        return unread;
    }
    
    public Integer getUnseen()
    {
        return unseen;
    }

    public CommentList getComments()
    {
        return comments;
    }

}