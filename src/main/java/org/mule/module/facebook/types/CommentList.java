 package org.mule.module.facebook.types;

import java.io.Serializable;
import java.util.List;

import com.restfb.Facebook;
import com.restfb.types.Comment;
 
public class CommentList implements Serializable
{
    @Facebook
    private List<Comment> data;
}

