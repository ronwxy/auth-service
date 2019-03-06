package com.springcloud.service.auth.config;

import com.springcloud.autoconfig.security.SharedSecurityConfigConfiguration;
import com.springcloud.service.auth.security.kick.AuthorizationServerTokenServicesProxy;
import com.springcloud.service.auth.security.kick.KickoutPolicyProperties;
import com.springcloud.service.auth.security.spi.CustClientDetailsService;
import com.springcloud.service.auth.service.base.ICustClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * oauth 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
@Import(SharedSecurityConfigConfiguration.class)
public class MyAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    /**
     * oauth2 password模式必须开启认证管理器支持
     *
     */
    private AuthenticationManager authenticationManager;

    /**
     */
    private TokenStore tokenStore;
    /**
     * @see AuthorizationServerEndpointsConfigurer#exceptionTranslator(WebResponseExceptionTranslator)
     */
    private WebResponseExceptionTranslator webResponseExceptionTranslator;

    /**
     * https://github.com/royclarkson/spring-rest-service-oauth/issues/19
     * to set int the endpoint configure to avoid refresh_token "UserDetailService is required" exception
     */
    private UserDetailsService userDetailsService;
    /**
     * https://stackoverflow.com/questions/31798631/stackoverflowerror-in-spring-oauth2-with-custom-clientdetailsservice
     */
    private ClientDetailsService clientDetailsService;
    private KickoutPolicyProperties kickOutPolicyProperties;

    @Bean
    public ClientDetailsService clientDetailsService(ICustClientDetailsService custClientDetailsService) {
        CustClientDetailsService myClientDetailsService = new CustClientDetailsService();
        myClientDetailsService.setClientDetailsService(custClientDetailsService);
        return myClientDetailsService;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @Autowired
    public void setWebResponseExceptionTranslator(WebResponseExceptionTranslator webResponseExceptionTranslator) {
        this.webResponseExceptionTranslator = webResponseExceptionTranslator;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    /**
     * 配置授权服务器
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).userDetailsService(userDetailsService)
                .tokenStore(tokenStore)
                .exceptionTranslator(webResponseExceptionTranslator);
        endpoints.setClientDetailsService(clientDetailsService);
        AuthorizationServerTokenServices old =
                endpoints.getDefaultAuthorizationServerTokenServices();
        endpoints.tokenServices(new AuthorizationServerTokenServicesProxy(
                kickOutPolicyProperties, old
        ));
    }

    @Autowired
    public void setKickOutPolicyProperties(KickoutPolicyProperties kickOutPolicyProperties) {
        this.kickOutPolicyProperties = kickOutPolicyProperties;
    }

    /**
     * 配置授权服务器的安全选项
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //--set key access roles and condition to access
        security.allowFormAuthenticationForClients();
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

//    @Configuration
//    public static class AuthenticationManagerConfig extends GlobalAuthenticationConfigurerAdapter {
//        @Autowired
//        private DaoAuthenticationProvider daoAuthenticationProvider;
//        @Autowired
//        private IUserService userService;
//        @Autowired
//        private UserDetailsService userDetailsService;
//        @Autowired
//        private IUser2OpenidService user2OpenidService;
//        @Autowired
//        private UserConverter userConverter;
//
//        @Override
//        public void init(AuthenticationManagerBuilder auth) throws Exception {
//            auth.authenticationProvider(daoAuthenticationProvider);
//            DaoAuthenticationProvider smsProvider = new DaoAuthenticationProvider() {
//                @Override
//                protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//                    //override to do not check password by sms;
//                }
//
//                @Override
//                public boolean supports(Class<?> authentication) {
//                    return super.supports(authentication) && AuthParamUtils.hasSMSCode();
//                }
//            };
//            smsProvider.setUserDetailsService(new SmsUserDetailsService(userService, userConverter));
//
//            auth.authenticationProvider(smsProvider);
//            DaoAuthenticationProvider openIdProvider = new DaoAuthenticationProvider() {
//                @Override
//                protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//                    //do not check password by sms;
//                }
//
//                @Override
//                public boolean supports(Class<?> authentication) {
//                    return super.supports(authentication) && AuthParamUtils.hasOpenId();
//                }
//            };
//            openIdProvider.setUserDetailsService(new OpenIdUserDetailsService(
//                    userService, user2OpenidService, userConverter
//            ));
//            auth.authenticationProvider(openIdProvider);
//        }
//    }
}
