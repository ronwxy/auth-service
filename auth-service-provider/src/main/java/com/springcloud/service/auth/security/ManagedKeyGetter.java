package com.springcloud.service.auth.security;

import com.springcloud.service.auth.util.AuthParamUtils;

/**
 * generate key for repository
 *
 * @param <T>
 * @author liubo
 */
public interface ManagedKeyGetter<T> extends MyApplication {
    String MANAGED_KEY_SEPARATOR = ":";

    String getKey(T t);

    /**
     * retrieve from web params,this is the default behavior;
     * @return appId
     */
    @Override
    default String getAppId() {
        return AuthParamUtils.getClientId();
    }
}
