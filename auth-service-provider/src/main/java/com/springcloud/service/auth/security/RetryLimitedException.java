package com.springcloud.service.auth.security;

import com.springcloud.service.auth.error.AuthError;
import org.springframework.security.core.AuthenticationException;

/**
 * retry policy exception
 *
 * @author liubo
 */
public class RetryLimitedException extends AuthenticationException {

    public RetryLimitedException(String explanation) {
        super(explanation);
    }

    public RetryLimitedException() {
        this(AuthError.LOGIN_RETRY_LIMIT.getMsg());
    }
}
