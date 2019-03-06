package com.springcloud.service.auth.service.spi.wechat;

import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

public class WechatAdapter implements org.springframework.social.connect.ApiAdapter<WeChat> {
    private final String openId;

    public WechatAdapter(){
        this(null);
    }
    public WechatAdapter(String openId) {
        this.openId = openId;
    }

    @Override
    public boolean test(WeChat api) {
        return true;
    }

    @Override
    public void setConnectionValues(WeChat api, ConnectionValues values) {
        WechatUserInfo userInfo = api.getUserInfo(openId);
        values.setProviderUserId(userInfo.getOpenid());
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getHeadimgurl());
    }

    @Override
    public UserProfile fetchUserProfile(WeChat api) {
        return null;
    }

    @Override
    public void updateStatus(WeChat api, String message) {

    }
}
