package com.springcloud.service.auth.service.spi.qq;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {
    /**
     * 获取code
     */
    private static final String QQ_URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    /**
     * 获取access_token 也就是令牌
     */
    private static final String QQ_URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/authorize";
    private String appId;

    public QQServiceProvider(String appId, String appSerect) {
        super(new QQOAuth2Template(appId,appSerect,QQ_URL_AUTHORIZE,QQ_URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String s) {
        return new QQImpl(s, appId);
    }
}
