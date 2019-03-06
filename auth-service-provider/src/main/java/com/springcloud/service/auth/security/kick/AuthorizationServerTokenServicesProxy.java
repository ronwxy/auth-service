package com.springcloud.service.auth.security.kick;

import com.springcloud.service.auth.domain.OAuth2ClientDetails;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Optional;

/**
 * proxy the origin {@link AuthorizationServerTokenServices},use different {@link KickoutPolicy} every client</br>
 * the default policy is {@link KickoutPolicy.NullKickoutPolicy}
 *
 * @see KickoutPolicy
 * @see org.springframework.security.oauth2.common.util.OAuth2Utils#CLIENT_ID
 * @see OAuth2ClientDetails#clientId
 */
public class AuthorizationServerTokenServicesProxy implements AuthorizationServerTokenServices {
    private KickoutPolicyProperties kickOutPolicyProperties;
    private AuthorizationServerTokenServices delegate;

    public AuthorizationServerTokenServicesProxy(KickoutPolicyProperties kickOutPolicyProperties, AuthorizationServerTokenServices delegate) {
        this.kickOutPolicyProperties = kickOutPolicyProperties;
        this.delegate = delegate;
    }

    private KickoutPolicy getKickOutPolicy(OAuth2Authentication oAuth2Authentication) {
        String clientId = oAuth2Authentication.getOAuth2Request().getClientId();
        KickoutPolicy kickOutPolicy = kickOutPolicyProperties.getKickoutPolicyMap().get(clientId);
        return Optional.ofNullable(kickOutPolicy).orElse(new KickoutPolicy.NullKickoutPolicy());
    }

    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        getKickOutPolicy(authentication).kickout(authentication);
        return delegate.createAccessToken(authentication);
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String refreshToken, TokenRequest tokenRequest) throws AuthenticationException {
        return delegate.refreshAccessToken(refreshToken, tokenRequest);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        return delegate.getAccessToken(authentication);
    }
}
