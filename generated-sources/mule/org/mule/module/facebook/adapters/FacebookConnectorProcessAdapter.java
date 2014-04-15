
package org.mule.module.facebook.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.module.facebook.FacebookConnector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>FacebookConnectorProcessAdapter</code> is a wrapper around {@link FacebookConnector } that enables custom processing strategies.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-15T03:23:29-05:00", comments = "Build master.1915.dd1962d")
public class FacebookConnectorProcessAdapter
    extends FacebookConnectorLifecycleAdapter
    implements ProcessAdapter<FacebookConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, FacebookConnectorCapabilitiesAdapter> getProcessTemplate() {
        final FacebookConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,FacebookConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, FacebookConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, FacebookConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
