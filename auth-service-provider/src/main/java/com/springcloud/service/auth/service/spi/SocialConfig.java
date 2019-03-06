package com.springcloud.service.auth.service.spi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

@Profile("prod")
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Profile("prod")
    @Bean
    public SpringSocialConfigurer springSocialConfigurer(){
        String filterProcessUrl = "";
        return null;
    }
    @Profile("prod")
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){
        return new ProviderSignInUtils(connectionFactoryLocator,getUsersConnectionRepository(connectionFactoryLocator));
    }
}
