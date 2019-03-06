package com.springcloud.service.auth.security;

import com.springcloud.service.auth.domain.OAuth2ClientDetails;

/**
 * application;</br>
 * all logical separated by {@code appId} must implements this interface;
 *
 * @author liubo
 */
public interface MyApplication {
    /**
     * @return appId, maybe {@link OAuth2ClientDetails#clientId}
     */
    String getAppId();
}
