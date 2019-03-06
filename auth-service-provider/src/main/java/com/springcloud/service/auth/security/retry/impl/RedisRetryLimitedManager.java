package com.springcloud.service.auth.security.retry.impl;

import com.google.common.base.Strings;
import com.springcloud.autoconfig.security.MyUserDetails;
import com.springcloud.service.auth.security.ManagedKeyGetter;
import com.springcloud.service.auth.security.RetryLimitedException;
import com.springcloud.service.auth.security.retry.IRetryLimitedManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.concurrent.TimeUnit;

/**
 * redis retry limited implement of {@link IRetryLimitedManager}</br>
 * use the auth database {@link RedisClientsNames#AUTH}
 *
 * @author liubo
 */
public class RedisRetryLimitedManager implements IRetryLimitedManager, ManagedKeyGetter<UserDetails> {
    private StringRedisTemplate redisTemplate;
    private int limitRetries = RETRY_LIMITED;

    public RedisRetryLimitedManager(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void retryLimited(UserDetails userDetails) throws RetryLimitedException {
        int oldRetries = getOldRetries(userDetails);
        if (isLimited(oldRetries)) {
            throw new RetryLimitedException();
        }
        setNewRetries(oldRetries, userDetails);
    }

    @Override
    public void setRetryLimited(int retryLimited) {
        this.limitRetries = retryLimited;
    }

    protected int getOldRetries(UserDetails userDetails) {
        String limit = redisTemplate.boundValueOps(getKey(userDetails)).get();
        return Strings.isNullOrEmpty(limit) ? 0 : Integer.valueOf(limit);
    }

    protected void setNewRetries(int oldRetries, UserDetails userDetails) {
        redisTemplate.boundValueOps(getKey(userDetails))
                .set(String.valueOf(oldRetries + 1),1, TimeUnit.DAYS);
    }

    protected boolean isLimited(int oldRetries) {
        return limitRetries == oldRetries;
    }

    @Override
    public String getKey(UserDetails userDetails) {
        MyUserDetails t = (MyUserDetails) userDetails;
        StringBuilder r = new StringBuilder("retry");
        r.append(MANAGED_KEY_SEPARATOR).append(getAppId());
        r.append(MANAGED_KEY_SEPARATOR).append(t.getUsername());
        r.append(MANAGED_KEY_SEPARATOR).append(t.getType());
        return r.toString();
    }

}
