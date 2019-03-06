package com.springcloud.service.auth.service.spi.sms;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 *
 * 短信验证码token
 */
public class SMSAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;


    public SMSAuthenticationToken(String mobile) {
        super(null);
        this.principal = mobile;
        super.setAuthenticated(false);
    }

    public SMSAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
