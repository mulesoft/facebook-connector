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
