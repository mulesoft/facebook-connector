
package org.mule.module.facebook.oauth;

import java.io.Serializable;
import javax.annotation.Generated;
import org.mule.api.store.ObjectStore;
import org.mule.common.security.oauth.OAuthState;
import org.mule.module.facebook.adapters.FacebookConnectorOAuth2Adapter;
import org.mule.security.oauth.BaseOAuthClientFactory;
import org.mule.security.oauth.OAuth2Adapter;
import org.mule.security.oauth.OAuth2Manager;

@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-04-24T12:40:55-05:00", comments = "Build master.1920.518defc")
public class FacebookConnectorOAuthClientFactory
    extends BaseOAuthClientFactory
{

    private FacebookConnectorOAuthManager oauthManager;

    public FacebookConnectorOAuthClientFactory(OAuth2Manager<OAuth2Adapter> oauthManager, ObjectStore<Serializable> objectStore) {
        super(oauthManager, objectStore);
        this.oauthManager = (FacebookConnectorOAuthManager) oauthManager;
    }

    @Override
    protected Class<? extends OAuth2Adapter> getAdapterClass() {
        return FacebookConnectorOAuth2Adapter.class;
    }

    @Override
    protected void setCustomAdapterProperties(OAuth2Adapter adapter, OAuthState state) {
        FacebookConnectorOAuth2Adapter connector = ((FacebookConnectorOAuth2Adapter) adapter);
        connector.setScope(oauthManager.getScope());
    }

    @Override
    protected void setCustomStateProperties(OAuth2Adapter adapter, OAuthState state) {
        FacebookConnectorOAuth2Adapter connector = ((FacebookConnectorOAuth2Adapter) adapter);
    }

}
