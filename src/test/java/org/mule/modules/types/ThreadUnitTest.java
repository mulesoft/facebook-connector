/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

 package org.mule.modules.types;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mule.module.facebook.types.CommentList;
import org.mule.module.facebook.types.NamedFacebookTypeList;
import org.mule.module.facebook.types.Thread;

import com.restfb.types.NamedFacebookType;
 
public class ThreadUnitTest
{
    private Thread thread;
    
    @Before
    public void setup()
    {
        this.thread = new Thread();
        thread.setComments(mock(CommentList.class));
        thread.setFrom(mock(NamedFacebookType.class));
        thread.setTo(mock(NamedFacebookTypeList.class));
        thread.setUnread(2);
        thread.setUnseen(3);
        thread.setUpdatedTime("2009-06-25T19:14:25+0000");
    }
    
    @Test
    public void testGettersAndSetters()
    {
        assertNotNull(thread.getComments());
        assertNotNull(thread.getFrom());
        assertNotNull(thread.getTo());
        assertNotNull(thread.getUnread());
        assertNotNull(thread.getUnseen());
        assertNotNull(thread.getUpdatedTime());
    }
}

