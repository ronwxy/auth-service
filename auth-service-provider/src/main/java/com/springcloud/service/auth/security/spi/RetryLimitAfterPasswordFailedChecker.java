package com.springcloud.service.auth.security.spi;

import com.springcloud.service.auth.security.AfterPasswordFailedChecker;
import com.springcloud.service.auth.security.retry.IRetryLimitedManager;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * retry limit pre check for authentication;
 *
 * @author liubo
 */
public class RetryLimitAfterPasswordFailedChecker implements AfterPasswordFailedChecker {
    private IRetryLimitedManager retryLimitedManager;

    public RetryLimitAfterPasswordFailedChecker(IRetryLimitedManager retryLimitedManager) {
        this.retryLimitedManager = retryLimitedManager;
    }

    @Override
    public void check(UserDetails toCheck) {
        retryLimitedManager.retryLimited(toCheck);
    }
}
