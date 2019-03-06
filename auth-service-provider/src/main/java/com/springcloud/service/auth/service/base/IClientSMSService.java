package com.springcloud.service.auth.service.base;

import com.springboot.autoconfig.tkmapper.service.IService;
import com.springcloud.service.auth.domain.ClientSMS;

public interface IClientSMSService extends IService<Long, ClientSMS> {

    ClientSMS findByClientId(String clientId);
}
