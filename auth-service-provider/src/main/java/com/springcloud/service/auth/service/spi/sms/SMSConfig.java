package com.springcloud.service.auth.service.spi.sms;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@ConditionalOnClass({
        SMSAuthenticationToken.class, SMSAuthenticationProvider.class,
        SMSAuthenticationFilter.class, SMSAuthenticationSecurityConfig.class
})
//@Configuration
public class SMSConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/form")
                .and()
                .apply(new SMSAuthenticationSecurityConfig())
                .and()
                .csrf().disable();
    }
}
