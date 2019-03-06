package com.springcloud.service.auth.security.kick;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * kick out policy
 *
 * @author liubo
 */
public interface KickoutPolicy {
    void kickout(OAuth2Authentication oAuth2Authentication);


    class NullKickoutPolicy implements KickoutPolicy {

        @Override
        public void kickout(OAuth2Authentication oAuth2Authentication) {

        }
    }
}
