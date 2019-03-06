package com.springcloud.service.auth.service.spi.wechat;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

public class WechatServiceProvider extends AbstractOAuth2ServiceProvider<WeChat> {
    /**
     * 微信获取授权码的url
     */
    private static final String WEIXIN_URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
    /**
     * 微信获取accessToken的url(微信在获取accessToken时也已经返回openId)
     */
    private static final String WEIXIN_URL_ACCESS_TOKEN = "https://controller.weixin.qq.com/sns/oauth2/access_token";

    public WechatServiceProvider(String appId, String appSecret) {
        super(new WechatOauth2Template(appId,appSecret,WEIXIN_URL_AUTHORIZE,WEIXIN_URL_ACCESS_TOKEN));
    }


    @Override
    public WeChat getApi(String accessToken) {
        return new WechatImpl(accessToken);
    }
}
