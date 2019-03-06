package com.springcloud.service.auth.service.spi.wechat;

public interface WeChat {
    //--openIdLogin;
    WechatUserInfo getUserInfo(String openId);
}
