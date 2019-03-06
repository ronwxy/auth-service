package com.springcloud.service.auth.service.spi.wechat;

public class WechatUserInfo {
    private String openid;
    private String nickname;
    private String headimgurl;

    public String getOpenid() {
        return openid;
    }

    public String getNickname() {
        return nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }
}
