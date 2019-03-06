package com.springcloud.service.auth.security;

import com.springboot.autoconfig.error.exception.ClientSideException;
import com.springcloud.service.auth.error.AuthError;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author liubo
 */
public class InvalidSmsCodeException extends OAuth2Exception {
    public InvalidSmsCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public InvalidSmsCodeException(String msg) {
        super(msg, new ClientSideException(AuthError.ERROR_SMS_CODE));
    }

    public InvalidSmsCodeException() {
        this(AuthError.ERROR_SMS_CODE.getMsg(), new ClientSideException(AuthError.ERROR_SMS_CODE));
    }

    @Override
    public String getOAuth2ErrorCode() {
        return AuthError.ERROR_SMS_CODE.getCode();
    }
}
