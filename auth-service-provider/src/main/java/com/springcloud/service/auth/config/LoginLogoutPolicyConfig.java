package com.springcloud.service.auth.config;

import com.springboot.autoconfig.redis.RedisClientFactory;
import com.springboot.autoconfig.redis.RedisClientsNames;
import com.springcloud.service.auth.security.kick.impl.ClientKickoutPolicy;
import com.springcloud.service.auth.security.lock.ILockManager;
import com.springcloud.service.auth.security.lock.impl.RedisLockManager;
import com.springcloud.service.auth.security.retry.IRetryLimitedManager;
import com.springcloud.service.auth.security.retry.impl.RedisRetryLimitedManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

@Configuration
public class LoginLogoutPolicyConfig {

    @Bean
    public ILockManager lockManager(RedisClientFactory redisClientFactory) {
        StringRedisTemplate redisTemplate = redisClientFactory.getInstance(RedisClientsNames.AUTH,
                StringRedisTemplate.class);
        return new RedisLockManager(redisTemplate);
    }

    @Bean
    public IRetryLimitedManager retryLimitedManager(RedisClientFactory redisClientFactory) {
        StringRedisTemplate redisTemplate = redisClientFactory.getInstance(RedisClientsNames.AUTH, StringRedisTemplate.class);
        return new RedisRetryLimitedManager(redisTemplate);
    }

    @Bean
    public ClientKickoutPolicy clientKickOutPolicy(@Qualifier("defaultAuthorizationServerTokenServices") AuthorizationServerTokenServices defaultAuthorizationServerTokenServices) {
        return new ClientKickoutPolicy(defaultAuthorizationServerTokenServices);
    }

}
