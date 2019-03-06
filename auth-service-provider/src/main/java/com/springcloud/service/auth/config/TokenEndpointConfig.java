package com.springcloud.service.auth.config;

import com.springcloud.service.auth.security.endpoint.RequestBodyTokenEndpoint;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

@Configuration
public class TokenEndpointConfig {
    @Bean
    public static TokenPointPostProcessor tokenPointPostProcessor() {
        return new TokenPointPostProcessor();
    }

    @Bean
    public RequestBodyTokenEndpoint requestBodyTokenEndpoint(
            AuthorizationServerEndpointsConfiguration configuration,
            ClientDetailsService clientDetailsService,
            WebResponseExceptionTranslator webResponseExceptionTranslator
    ) {
        AuthorizationServerEndpointsConfigurer configurer = configuration.getEndpointsConfigurer();
        RequestBodyTokenEndpoint requestBodyTokenEndpoint = new RequestBodyTokenEndpoint();
        requestBodyTokenEndpoint.setClientDetailsService(clientDetailsService);
        requestBodyTokenEndpoint.setProviderExceptionHandler(webResponseExceptionTranslator);
        requestBodyTokenEndpoint.setTokenGranter(configurer.getTokenGranter());
        requestBodyTokenEndpoint.setOAuth2RequestFactory(configurer.getOAuth2RequestFactory());
        requestBodyTokenEndpoint.setOAuth2RequestValidator(configurer.getOAuth2RequestValidator());
        requestBodyTokenEndpoint.setAllowedRequestMethods(configurer.getAllowedTokenEndpointRequestMethods());
        return requestBodyTokenEndpoint;
    }

    private static class TokenPointPostProcessor implements BeanFactoryPostProcessor {

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            if (BeanDefinitionRegistry.class.isAssignableFrom(beanFactory.getClass())) {
                BeanDefinitionRegistry bdr = (BeanDefinitionRegistry) beanFactory;
                BeanDefinition bd = bdr.getBeanDefinition("tokenEndpoint");
                if (bd != null) {
                    //remove the default tokenEndpoint;
                    bdr.removeBeanDefinition("tokenEndpoint");
                }
            }
        }
    }
}
