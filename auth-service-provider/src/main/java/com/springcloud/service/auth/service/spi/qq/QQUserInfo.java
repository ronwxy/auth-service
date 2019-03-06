package com.springcloud.service.auth.service.spi.qq;

public class QQUserInfo {
    private String openId;
    private String figureurl_qq_1;
    private String nickName;

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }

    public String getNickName() {
        return nickName;
    }

    public String getFigureurl_qq_1() {
        return figureurl_qq_1;
    }
}
