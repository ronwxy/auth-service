package com.springcloud.service.auth.service.spi.wechat;

import org.springframework.social.oauth2.AccessGrant;

public class WechatAccessGrant extends AccessGrant {
    private String openId;
    public WechatAccessGrant(String openId) {
        super("");
    }

    public WechatAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }
}
