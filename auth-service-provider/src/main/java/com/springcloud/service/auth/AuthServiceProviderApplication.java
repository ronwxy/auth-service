package com.springcloud.service.auth;

import com.springboot.autoconfig.redis.EnableRedisClients;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
@EnableRedisClients
public class AuthServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceProviderApplication.class, args);
    }

}
