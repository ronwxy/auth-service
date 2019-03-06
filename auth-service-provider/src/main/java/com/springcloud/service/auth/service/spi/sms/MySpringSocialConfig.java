package com.springcloud.service.auth.service.spi.sms;

import org.springframework.context.annotation.Profile;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

@Profile("prod")
public class MySpringSocialConfig extends SpringSocialConfigurer {
    private String filterProcessUrl;

    public MySpringSocialConfig(String filterProcessUrl) {
        this.filterProcessUrl = filterProcessUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessUrl);
        filter.setSignupUrl("/register");
        return (T) filter;
    }

}
