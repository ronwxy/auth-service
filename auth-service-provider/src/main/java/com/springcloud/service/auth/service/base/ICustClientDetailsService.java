package com.springcloud.service.auth.service.base;

import com.springboot.autoconfig.tkmapper.service.IService;
import com.springcloud.service.auth.domain.OAuth2ClientDetails;

public interface ICustClientDetailsService extends IService<Long, OAuth2ClientDetails> {
    OAuth2ClientDetails findClientByClientId(String clientId);
}
