package com.springcloud.service.auth.security.lock;

import com.springcloud.service.auth.domain.User;

/**
 * manager account lock;
 *
 * @author liubo
 */
public interface ILockManager {
    //lock for 30 minutes;
    long LOCK_PERIOD_TIME = 30 * 60;

    /**
     * check the user is locked;
     *
     * @param user
     * @return
     */
    boolean isLocked(User user);

    /**
     * lock user;
     *
     * @param user
     */
    void lock(User user);

    /**
     * unlock the user
     *
     * @param user
     */
    void unlock(User user);

    /**
     * set lock period time</br>
     * default is {@link ILockManager#LOCK_PERIOD_TIME}
     *
     *
     * @param seconds
     */
    void setLockPeriodTime(long seconds);
}
