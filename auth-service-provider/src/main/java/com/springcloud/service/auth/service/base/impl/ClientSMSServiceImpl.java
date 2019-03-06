package com.springcloud.service.auth.service.base.impl;

import com.springboot.autoconfig.tkmapper.service.BaseService;
import com.springcloud.service.auth.domain.ClientSMS;
import com.springcloud.service.auth.service.base.IClientSMSService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ClientSMSServiceImpl extends BaseService<Long, ClientSMS> implements IClientSMSService {
    @Override
    public ClientSMS findByClientId(String clientId) {
        if (StringUtils.isEmpty(clientId)) {
            return null;
        }
        ClientSMS clientSMS = new ClientSMS();
        clientSMS.setClientId(clientId);
        return selectOne(clientSMS);

    }
}
