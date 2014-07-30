/**
 * (c) 2003-2014 MuleSoft, Inc. The software in this package is published under
 * the terms of the CPAL v1.0 license, a copy of which has been included with this
 * distribution in the LICENSE.md file.
 */

package org.mule.module.facebook.demo;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.transport.PropertyScope;
import org.mule.tck.junit4.FunctionalTestCase;

public class FacebookFunctionalTestDriver extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "mule-config.xml";
    }

    @Test
    public void getPhotoFromFbAndSendToAS2() throws Exception
    {
        final MuleEvent testEvent = getTestEvent(null);
        testEvent.getMessage().setProperty("photo", "347528201978388", PropertyScope.INBOUND);
        MessageProcessor flow = lookupFlowConstruct("get-photo-and-send-to-as2");
        MuleEvent result = flow.process(testEvent);
        assertNotNull(result.getMessage());
    }

    private MessageProcessor lookupFlowConstruct(final String name)
    {
        return (MessageProcessor) muleContext.getRegistry().lookupFlowConstruct(name);
    }

}