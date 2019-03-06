package com.springcloud.service.auth.service.spi.sms;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * mobile authenticate provider;
 */
public class SMSAuthenticationProvider implements AuthenticationProvider ,InitializingBean{

    private UserDetailsService userDetailsService;

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SMSAuthenticationToken token = (SMSAuthenticationToken) authentication;
        UserDetails userDetails = userDetailsService.loadUserByUsername((String) token.getPrincipal());
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("can not found user details");
        }
        SMSAuthenticationToken result = new SMSAuthenticationToken(userDetails.getAuthorities(),token.getPrincipal());
        result.setDetails(token.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SMSAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(getUserDetailsService()==null)
            throw new IllegalArgumentException("you must set UserDetailsService first to use this class");
    }
}
