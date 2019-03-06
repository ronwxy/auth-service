package com.springcloud.service.auth.security.kick.impl;

import com.springcloud.service.auth.security.kick.KickoutPolicy;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * use {@link AuthorizationServerTokenServices#refreshAccessToken(String, TokenRequest)} to kick out the old access_token;
 *
 */
public class ClientKickoutPolicy implements KickoutPolicy {
    private AuthorizationServerTokenServices delegate;

    public ClientKickoutPolicy(AuthorizationServerTokenServices delegate) {
        this.delegate = delegate;
    }

    @Override
    public void kickout(OAuth2Authentication oAuth2Authentication) {
        OAuth2AccessToken token = delegate.getAccessToken(oAuth2Authentication);
        if (token != null) {
            OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
            TokenRequest request = new TokenRequest(oAuth2Request.getRequestParameters(),
                    oAuth2Request.getClientId(), oAuth2Request.getScope(),
                    oAuth2Request.getGrantType());
            delegate.refreshAccessToken(token.getRefreshToken().getValue(), request);
        }
    }
}
