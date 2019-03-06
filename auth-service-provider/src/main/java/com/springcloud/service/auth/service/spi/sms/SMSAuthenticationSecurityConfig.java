package com.springcloud.service.auth.service.spi.sms;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * configure the sms
 *
 * @author liubo
 */
public class SMSAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        //--sms filter settings
        SMSAuthenticationFilter filter = new SMSAuthenticationFilter();
        filter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationFailureHandler(builder.getSharedObject(AuthenticationFailureHandler.class));
        //--sms authorize provider
        SMSAuthenticationProvider provider = new SMSAuthenticationProvider();
        provider.setUserDetailsService(builder.getSharedObject(UserDetailsService.class));
        //--after UsernamePasswordAuthenticationFilter
        builder.authenticationProvider(provider)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
