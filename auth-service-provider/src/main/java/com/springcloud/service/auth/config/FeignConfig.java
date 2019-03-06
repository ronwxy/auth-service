package com.springcloud.service.auth.config;

import com.springcloud.api.notify.api.SMSClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = SMSClient.class)
public class FeignConfig {
}
