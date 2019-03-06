package com.springcloud.service.auth.security;

import com.springcloud.autoconfig.security.SecurityExceptionCodeEnum;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class InvalidClientSMSException extends OAuth2Exception {
    public InvalidClientSMSException(String msg, Throwable t) {
        super(msg, t);
    }

    public InvalidClientSMSException(String msg) {
        super(msg);
    }

    public InvalidClientSMSException() {
        super(SecurityExceptionCodeEnum.invalid_client.getMsg());
    }
}
