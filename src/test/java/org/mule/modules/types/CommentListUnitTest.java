/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

 package org.mule.modules.types;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mule.module.facebook.types.CommentList;

import com.restfb.types.Comment;
 
public class CommentListUnitTest
{
    private List<Comment> data = new ArrayList<Comment>();
    
    @Before
    public void setup()
    {
        data.add(mock(Comment.class));
        data.add(mock(Comment.class));
    }
    
    @Test
    public void testData()
    {
        CommentList commentList = new CommentList();
        commentList.setData(data);
        assertEquals(commentList.getData(), data);
    }
}
