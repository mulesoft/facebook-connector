
package org.mule.module.facebook.processors;

import javax.annotation.Generated;
import org.mule.api.construct.FlowConstructAware;
import org.mule.api.processor.MessageProcessor;
import org.mule.module.facebook.oauth.FacebookConnectorOAuthManager;
import org.mule.security.oauth.processor.BaseOAuth2UnauthorizeMessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-16T10:42:49-05:00", comments = "Build master.1915.dd1962d")
public class UnauthorizeMessageProcessor
    extends BaseOAuth2UnauthorizeMessageProcessor
    implements FlowConstructAware, MessageProcessor
{

    private static Logger logger = LoggerFactory.getLogger(UnauthorizeMessageProcessor.class);

    @Override
    protected Class<FacebookConnectorOAuthManager> getOAuthManagerClass() {
        return FacebookConnectorOAuthManager.class;
    }

}
