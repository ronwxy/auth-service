package com.springcloud.service.auth.security.kick;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "auth.kickout")
public class KickoutPolicyProperties implements BeanFactoryAware, SmartInitializingSingleton {
    private Map<String, Class<KickoutPolicy>> kickoutPolicyClassMap = new HashMap<>();
    private Map<String, KickoutPolicy> kickoutPolicyMap = new HashMap<>();
    private BeanFactory beanFactory;

    public void setKickoutPolicyClassMap(Map<String, Class<KickoutPolicy>> kickOutPolicyClassMap) {
        this.kickoutPolicyClassMap = kickOutPolicyClassMap;
    }

    public Map<String, Class<KickoutPolicy>> getKickoutPolicyClassMap() {
        return kickoutPolicyClassMap;
    }

    public Map<String, KickoutPolicy> getKickoutPolicyMap() {
        return kickoutPolicyMap;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterSingletonsInstantiated() {
        kickoutPolicyClassMap.forEach((k, v) -> kickoutPolicyMap.put(k, beanFactory.getBean(v)));
    }
}
