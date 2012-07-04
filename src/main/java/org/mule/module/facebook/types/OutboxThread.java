 package org.mule.module.facebook.types;

import com.restfb.Facebook;
 
public class OutboxThread extends Thread
{
    private static final long serialVersionUID = 1L;
    
    @Facebook
    private String message;

    public String getMessage()
    {
        return message;
    }
}

