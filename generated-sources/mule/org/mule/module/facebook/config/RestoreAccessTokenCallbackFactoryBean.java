
package org.mule.module.facebook.config;

import javax.annotation.Generated;
import org.mule.api.processor.MessageProcessor;
import org.mule.config.spring.factories.MessageProcessorChainFactoryBean;
import org.mule.security.oauth.callback.DefaultRestoreAccessTokenCallback;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-15T03:23:29-05:00", comments = "Build master.1915.dd1962d")
public class RestoreAccessTokenCallbackFactoryBean
    extends MessageProcessorChainFactoryBean
{


    public Class getObjectType() {
        return DefaultRestoreAccessTokenCallback.class;
    }

    public Object getObject()
        throws Exception
    {
        DefaultRestoreAccessTokenCallback callback = new DefaultRestoreAccessTokenCallback();
        callback.setMessageProcessor(((MessageProcessor) super.getObject()));
        return callback;
    }

}
