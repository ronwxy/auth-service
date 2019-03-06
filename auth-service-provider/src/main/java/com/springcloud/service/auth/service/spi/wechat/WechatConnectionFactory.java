package com.springcloud.service.auth.service.spi.wechat;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

public class WechatConnectionFactory extends OAuth2ConnectionFactory<WeChat> {
    public WechatConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId,new WechatServiceProvider(appId,appSecret),new WechatAdapter());
    }

    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if(accessGrant instanceof WechatAccessGrant) {
            return ((WechatAccessGrant) accessGrant).getOpenId();
        }
        return null;
    }

    @Override
    public Connection<WeChat> createConnection(ConnectionData data) {
       return new OAuth2Connection<WeChat>(data,(OAuth2ServiceProvider<WeChat>)getServiceProvider(),new WechatAdapter(data.getProviderId()));
    }

}
