package com.springcloud.service.auth.config;

import com.springcloud.service.auth.security.spi.openid.OpenIdUserDetailsService;
import com.springcloud.service.auth.security.spi.support.UserConverter;
import com.springcloud.service.auth.service.base.IUser2OpenidService;
import com.springcloud.service.auth.service.base.IUserService;
import com.springcloud.service.auth.util.AuthParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.LinkedHashMap;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private static final String ant_login_path = "/login/*";

    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private IUser2OpenidService user2OpenidService;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        DaoAuthenticationProvider smsProvider = new DaoAuthenticationProvider() {
            @Override
            protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
                //override to do not check password by sms;
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return super.supports(authentication) && AuthParamUtils.hasSMSCode();
            }

        };
        smsProvider.setUserDetailsService(userDetailsService);

        auth.authenticationProvider(smsProvider);
        DaoAuthenticationProvider openIdProvider = new DaoAuthenticationProvider() {
            @Override
            protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
                //do not check password by sms;
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return super.supports(authentication) && AuthParamUtils.hasOpenId();
            }
        };
        openIdProvider.setUserDetailsService(new OpenIdUserDetailsService(
                userService, user2OpenidService, userConverter
        ));
        auth.authenticationProvider(openIdProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(
                new ClientDetailsUserDetailsService(clientDetailsService)
        );
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        AuthenticationManager authenticationManager = new ProviderManager(
                Arrays.asList(daoAuthenticationProvider)
        );
        LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> entryPointMap
                = new LinkedHashMap<>();
        BasicAuthenticationEntryPoint basicAuthenticationEntryPoint = new BasicAuthenticationEntryPoint();
        basicAuthenticationEntryPoint.setRealmName("Realm");
        entryPointMap.put(new AntPathRequestMatcher(ant_login_path), basicAuthenticationEntryPoint);

        DelegatingAuthenticationEntryPoint delegatingAuthenticationEntryPoint =
                new DelegatingAuthenticationEntryPoint(entryPointMap);

        //用作client认证，提供Principal给login/*接口，只有通过client认证才会调用到login/*接口
        BasicAuthenticationFilter basicAuthenticationFilter = new BasicAuthenticationFilter(
                authenticationManager, delegatingAuthenticationEntryPoint
        );
        http.requestMatchers()
                .antMatchers(ant_login_path)
                .and()
                .addFilterAfter(basicAuthenticationFilter, LogoutFilter.class)
                .exceptionHandling().authenticationEntryPoint(delegatingAuthenticationEntryPoint)
                .and()
                .authorizeRequests().anyRequest().fullyAuthenticated()
                .and()
                .csrf().disable();
    }
}
