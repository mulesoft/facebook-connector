 package org.mule.modules.types;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mule.module.facebook.types.OutboxThread;
 
public class OutboxThreadUnitTest
{
    private OutboxThread outboxThread;
    
    @Before
    public void setup()
    {
        outboxThread = new OutboxThread();
    }
    
    @Test
    public void testGettersAndSetters()
    {
        final String message = "hello world!";
        outboxThread.setMessage(message);
        assertEquals(outboxThread.getMessage(), message);
    }
}

