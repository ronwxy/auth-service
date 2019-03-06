package com.springcloud.service.auth.error;


import com.springboot.autoconfig.error.BaseErrors;

public enum AuthError implements BaseErrors {
    INVALID_PHONE("非法的手机号码"),
    INVALID_USERNAME("非法的用户名"),
    INVALID_EMAIL("非法的email"),
    INVALID_USER_TYPE("非法的用户类型"),
    INVALID_PASSWORD("非法的密码"),
    REQUIRED_SMS_CODE("缺少手机验证码"),
    REQUIRED_USER_TYPE("缺少用户类型"),
    REQUIRED_APP_ID("缺少应用类型"),
    /**
     * @since 2.0
     */
    REQUIRED_OPEN_ID("缺少openId"),
    REQUIRED_CHANNEL("缺少渠道"),
    REQUIRED_USERNAME("缺少用户名"),
    REQUIRED_PASSWORD("缺少密码"),
    REQUIRED_PHONE("缺少手机号码"),


    ERROR_SMS_CODE("手机验证码错误"),
    ROLE_NOT_EXISTS("角色不存在"),
    USER_NOT_EXISTS("用户不存在"),
    BAD_CREDENTIALS("用户名或密码不正确"),
    LOGIN_RETRY_LIMIT("用户密码重试次数超过限制"),
    USER_BLOCKED("用户被禁用"),
    USER_LOCK("用户被锁定"),
    EXISTS_USERNAME_TYPE("此类型下存在同名的用户名"),
    EXISTS_EMAIL_TYPE("此类型下存在同名email"),
    EXISTS_PHONE_TYPE("此类型下存在相同的电话号码");
    private String msg;

    AuthError(String msg) {
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return name().toLowerCase();
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
