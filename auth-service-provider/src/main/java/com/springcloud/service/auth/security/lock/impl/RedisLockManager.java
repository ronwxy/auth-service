package com.springcloud.service.auth.security.lock.impl;

import com.springcloud.service.auth.domain.User;
import com.springcloud.service.auth.security.ManagedKeyGetter;
import com.springcloud.service.auth.security.lock.ILockManager;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * redis implement {@link ILockManager}</br>
 * use the auth database {@link RedisClientsNames#AUTH}
 *
 * @author liubo
 */
public class RedisLockManager implements ILockManager, ManagedKeyGetter<User> {
    private StringRedisTemplate redisTemplate;
    private long lockPeriodTime = LOCK_PERIOD_TIME;

    public RedisLockManager(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean isLocked(User user) {
        return redisTemplate.hasKey(getKey(user));
    }

    @Override
    public void lock(User user) {
        redisTemplate.boundValueOps(getKey(user))
                .set(Boolean.FALSE.toString(), lockPeriodTime, TimeUnit.SECONDS);
    }

    @Override
    public void unlock(User user) {
        boolean locked = isLocked(user);
        if (locked) {
            redisTemplate.delete(getKey(user));
        }
    }

    @Override
    public void setLockPeriodTime(long seconds) {
        this.lockPeriodTime = seconds;
    }


    @Override
    public String getKey(User t) {
        StringBuilder r = new StringBuilder("lock");
        r.append(MANAGED_KEY_SEPARATOR).append(getAppId());
        r.append(MANAGED_KEY_SEPARATOR).append(t.getUsername());
        r.append(MANAGED_KEY_SEPARATOR).append(t.getType());
        return r.toString();
    }
}
