package org.mule.module.facebook.connection.strategy;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.oauth.*;
import org.mule.api.annotations.param.Default;


/**
 * OAuth v2 strategy to connect against Facebook API
 */
@OAuth2(friendlyName = "OAuth v2.0"
        , accessTokenUrl = "https://graph.facebook.com/oauth/access_token"
        , authorizationUrl = "https://graph.facebook.com/oauth/authorize"
        , accessTokenRegex = "access_token=([^&]+?)(&|$)"
        , expirationRegex = "expires=([^&]+?)(&|$)")
public class FacebookOAuthStrategy {

    /**
     * The application identifier as registered with Facebook
     */
    @Configurable
    @OAuthConsumerKey
    private String consumerKey;

    /**
     * The application secret
     */
    @Configurable
    @OAuthConsumerSecret
    private String consumerSecret;

    /**
     * Facebook permissions
     */
    @Configurable
    @Default(value = "email,read_stream,publish_stream")
    @OAuthScope
    private String scope;

    @OAuthAccessToken
    private String accessToken;

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
