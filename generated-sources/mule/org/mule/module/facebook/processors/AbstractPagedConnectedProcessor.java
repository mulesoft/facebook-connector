
package org.mule.module.facebook.processors;

import java.lang.reflect.Type;
import javax.annotation.Generated;
import org.mule.streaming.processor.AbstractDevkitBasedPageableMessageProcessor;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-24T12:40:55-05:00", comments = "Build master.1920.518defc")
public abstract class AbstractPagedConnectedProcessor
    extends AbstractDevkitBasedPageableMessageProcessor
    implements ConnectivityProcessor
{


    public AbstractPagedConnectedProcessor(String operationName) {
        super(operationName);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Type typeFor(String fieldName)
        throws NoSuchFieldException
    {
        return AbstractPagedConnectedProcessor.class.getDeclaredField(fieldName).getGenericType();
    }

}
