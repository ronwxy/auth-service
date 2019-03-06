package com.springcloud.service.auth.service.spi.qq;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

public class QQConnectFactory extends OAuth2ConnectionFactory<QQ> {
    public QQConnectFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret),new QQAdapter());
    }
}
