
package org.mule.module.facebook.oauth;

import java.io.Serializable;
import javax.annotation.Generated;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.expression.ExpressionManager;
import org.mule.api.store.ObjectStore;
import org.mule.module.facebook.FacebookConnector;
import org.mule.module.facebook.adapters.FacebookConnectorOAuth2Adapter;
import org.mule.security.oauth.BaseOAuth2Manager;
import org.mule.security.oauth.OAuth2Adapter;
import org.mule.security.oauth.OAuth2Manager;
import org.mule.security.oauth.OnNoTokenPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A {@code FacebookConnectorOAuthManager} is a wrapper around {@link FacebookConnector } that adds access token management capabilities to the pojo.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T04:37:34-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class FacebookConnectorOAuthManager
    extends BaseOAuth2Manager<OAuth2Adapter>
{

    private static Logger logger = LoggerFactory.getLogger(FacebookConnectorOAuthManager.class);
    private final static String MODULE_NAME = "Facebook";
    private final static String MODULE_VERSION = "2.3.2-SNAPSHOT";
    private final static String DEVKIT_VERSION = "3.5.0-SNAPSHOT";
    private final static String DEVKIT_BUILD = "UNKNOWN_BUILDNUMBER";

    @Override
    protected Logger getLogger() {
        return logger;
    }

    /**
     * Sets consumerKey
     * 
     * @param key to set
     */
    public void setConsumerKey(String value) {
        super.setConsumerKey(value);
    }

    /**
     * Sets consumerSecret
     * 
     * @param secret to set
     */
    public void setConsumerSecret(String value) {
        super.setConsumerSecret(value);
    }

    /**
     * Sets scope
     * 
     * @param scope to set
     */
    public void setScope(String value) {
        super.setScope(value);
    }

    public String getModuleName() {
        return MODULE_NAME;
    }

    public String getModuleVersion() {
        return MODULE_VERSION;
    }

    public String getDevkitVersion() {
        return DEVKIT_VERSION;
    }

    public String getDevkitBuild() {
        return DEVKIT_BUILD;
    }

    @Override
    protected OAuth2Adapter instantiateAdapter() {
        return new FacebookConnectorOAuth2Adapter(this);
    }

    @Override
    protected KeyedPoolableObjectFactory createPoolFactory(OAuth2Manager<OAuth2Adapter> oauthManager, ObjectStore<Serializable> objectStore) {
        return new FacebookConnectorOAuthClientFactory(oauthManager, objectStore);
    }

    @Override
    protected void setCustomProperties(OAuth2Adapter adapter) {
        FacebookConnectorOAuth2Adapter connector = ((FacebookConnectorOAuth2Adapter) adapter);
        connector.setConsumerKey(getConsumerKey());
        connector.setConsumerSecret(getConsumerSecret());
        connector.setScope(getScope());
    }

    protected void fetchCallbackParameters(OAuth2Adapter adapter, String response) {
        FacebookConnectorOAuth2Adapter connector = ((FacebookConnectorOAuth2Adapter) adapter);
        ExpressionManager expressionManager = (muleContext.getExpressionManager());
        MuleMessage muleMessage = new DefaultMuleMessage(response, (muleContext));
    }

    public void setOnNoToken(OnNoTokenPolicy policy) {
        this.getDefaultUnauthorizedConnector().setOnNoTokenPolicy(policy);
    }

}
