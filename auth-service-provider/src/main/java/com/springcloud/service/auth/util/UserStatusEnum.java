package com.springcloud.service.auth.util;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-11 下午3:19
 * <p>Version: 1.0
 */
public enum UserStatusEnum {

    normal("正常状态"), blocked("封禁状态");

    private final String info;

    private UserStatusEnum(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}