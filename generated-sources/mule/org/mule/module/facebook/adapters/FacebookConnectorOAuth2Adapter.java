
package org.mule.module.facebook.adapters;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.Generated;
import org.apache.log4j.Logger;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.common.security.oauth.AuthorizationParameter;
import org.mule.common.security.oauth.exception.NotAuthorizedException;
import org.mule.common.security.oauth.exception.UnableToAcquireAccessTokenException;
import org.mule.module.facebook.FacebookConnector;
import org.mule.security.oauth.OAuth2Adapter;
import org.mule.security.oauth.OAuth2Connector;
import org.mule.security.oauth.OAuth2Manager;
import org.mule.security.oauth.OnNoTokenPolicy;


/**
 * A {@code FacebookConnectorOAuth2Adapter} is a wrapper around {@link FacebookConnector } that adds OAuth capabilites to the pojo.
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T12:05:56-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class FacebookConnectorOAuth2Adapter
    extends FacebookConnectorProcessAdapter
    implements OAuth2Adapter, OAuth2Connector
{

    private OAuth2Manager<OAuth2Adapter> oauthManager;
    private final static Pattern ACCESS_CODE_PATTERN = Pattern.compile("access_token=([^&]+?)&");
    private final static Pattern REFRESH_TOKEN_PATTERN = Pattern.compile("\"refresh_token\":\"([^&]+?)\"");
    private final static Pattern EXPIRATION_TIME_PATTERN = Pattern.compile("expires=([^&]+?)$");
    private String name;
    private OnNoTokenPolicy onNoTokenPolicy;
    private String oauthVerifier;
    private String refreshToken;
    public String redirectUri;
    private String authorizationUrl = null;
    private String accessTokenUrl = null;
    private Date expiration;
    private final static Logger LOGGER = Logger.getLogger(FacebookConnectorOAuth2Adapter.class);

    public FacebookConnectorOAuth2Adapter(OAuth2Manager<OAuth2Adapter> oauthManager) {
        this.oauthManager = oauthManager;
    }

    /**
     * Retrieves ACCESS_CODE_PATTERN
     * 
     */
    @Override
    public Pattern getAccessCodePattern() {
        return this.ACCESS_CODE_PATTERN;
    }

    /**
     * Retrieves REFRESH_TOKEN_PATTERN
     * 
     */
    @Override
    public Pattern getRefreshTokenPattern() {
        return this.REFRESH_TOKEN_PATTERN;
    }

    /**
     * Retrieves EXPIRATION_TIME_PATTERN
     * 
     */
    @Override
    public Pattern getExpirationTimePattern() {
        return this.EXPIRATION_TIME_PATTERN;
    }

    /**
     * Retrieves name
     * 
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets name
     * 
     * @param value Value to set
     */
    @Override
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Retrieves onNoTokenPolicy
     * 
     */
    public OnNoTokenPolicy getOnNoTokenPolicy() {
        return this.onNoTokenPolicy;
    }

    /**
     * Sets onNoTokenPolicy
     * 
     * @param value Value to set
     */
    public void setOnNoTokenPolicy(OnNoTokenPolicy value) {
        this.onNoTokenPolicy = value;
    }

    /**
     * Retrieves oauthVerifier
     * 
     */
    public String getOauthVerifier() {
        return this.oauthVerifier;
    }

    /**
     * Sets oauthVerifier
     * 
     * @param value Value to set
     */
    public void setOauthVerifier(String value) {
        this.oauthVerifier = value;
    }

    /**
     * Retrieves refreshToken
     * 
     */
    public String getRefreshToken() {
        return this.refreshToken;
    }

    /**
     * Sets refreshToken
     * 
     * @param value Value to set
     */
    public void setRefreshToken(String value) {
        this.refreshToken = value;
    }

    /**
     * Sets authorizationUrl
     * 
     * @param value Value to set
     */
    public void setAuthorizationUrl(String value) {
        this.authorizationUrl = value;
    }

    /**
     * Retrieves authorizationUrl
     * 
     */
    public String getAuthorizationUrl() {
        return this.authorizationUrl;
    }

    /**
     * Sets accessTokenUrl
     * 
     * @param value Value to set
     */
    public void setAccessTokenUrl(String value) {
        this.accessTokenUrl = value;
    }

    /**
     * Retrieves accessTokenUrl
     * 
     */
    public String getAccessTokenUrl() {
        return this.accessTokenUrl;
    }

    /**
     * Sets expiration
     * 
     * @param value Value to set
     */
    public void setExpiration(Date value) {
        this.expiration = value;
    }

    public void initialise()
        throws InitialisationException
    {
        super.initialise();
    }

    public String authorize(Map<String, String> extraParameters, String authorizationUrl, String redirectUri) {
        return oauthManager.buildAuthorizeUrl(extraParameters, authorizationUrl, redirectUri);
    }

    public void fetchAccessToken(String redirectUri)
        throws UnableToAcquireAccessTokenException
    {
        oauthManager.fetchAccessToken(this, redirectUri);
    }

    @Override
    public void refreshAccessToken(String accessTokenId)
        throws UnableToAcquireAccessTokenException
    {
        oauthManager.refreshAccessToken(this, accessTokenId);
    }

    public boolean hasTokenExpired() {
        return ((expiration!= null)&&expiration.before(new Date()));
    }

    public void reset() {
        expiration = null;
        oauthVerifier = null;
        setAccessToken(null);
    }

    public void hasBeenAuthorized()
        throws NotAuthorizedException
    {
        if (getAccessToken() == null) {
            throw new NotAuthorizedException("This connector has not yet been authorized, please authorize by calling \"authorize\".");
        }
    }

    @Override
    public Set<AuthorizationParameter<?>> getAuthorizationParameters() {
        Set<AuthorizationParameter<?>> result = new HashSet<AuthorizationParameter<?>>();
        return result;
    }

    @Override
    public void postAuth() {
    }

    @Override
    public<P >ProcessTemplate<P, FacebookConnectorCapabilitiesAdapter> getProcessTemplate() {
        throw new RuntimeException();
    }
    @Override
    public String getAccessTokenRegex() {
        return ACCESS_CODE_PATTERN.pattern();
    }

    @Override
    public String getExpirationRegex() {
        return EXPIRATION_TIME_PATTERN.pattern();
    }

    @Override
    public String getRefreshTokenRegex() {
        return REFRESH_TOKEN_PATTERN.pattern();
    }

    @Override
    public String getVerifierRegex() {
        return oauthVerifier;
    }
}
