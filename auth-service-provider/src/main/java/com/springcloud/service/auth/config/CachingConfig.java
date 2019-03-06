package com.springcloud.service.auth.config;

import com.springboot.autoconfig.redis.RedisClientFactory;
import com.springboot.autoconfig.redis.RedisClientsNames;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@Configuration
@EnableCaching(proxyTargetClass = true)
public class CachingConfig {
//    @Bean
//    @Primary
//    public CacheManager cacheManager(RedisClientFactory redisClientFactory) {
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisClientFactory.getInstance(RedisClientsNames.AUTH, RedisConnectionFactory.class));
//        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer(Charset.defaultCharset());
//        redisTemplate.setKeySerializer(stringRedisSerializer);
//        redisTemplate.setHashKeySerializer(stringRedisSerializer);
//        redisTemplate.afterPropertiesSet();
//        CacheManager cacheManager = new RedisCacheManager(redisTemplate);
//        ((RedisCacheManager) cacheManager).setDefaultExpiration(60);
//        return cacheManager;
//    }

    @Bean
    @Primary
    public CacheManager cacheManager(RedisClientFactory redisClientFactory) {
        RedisConnectionFactory connectionFactory = redisClientFactory.getInstance(RedisClientsNames.AUTH, RedisConnectionFactory.class);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30));
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }
}
