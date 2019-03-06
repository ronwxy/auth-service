package com.springcloud.service.auth.security.retry;

import com.springcloud.service.auth.security.RetryLimitedException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * retry limit policy;
 *
 * @author liubo
 */
public interface IRetryLimitedManager {

    int RETRY_LIMITED = 5;

    void retryLimited(UserDetails userDetails) throws RetryLimitedException;

    void setRetryLimited(int retryLimited);

}
