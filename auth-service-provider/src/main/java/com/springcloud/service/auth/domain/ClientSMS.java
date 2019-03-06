package com.springcloud.service.auth.domain;

import com.springboot.autoconfig.tkmapper.domain.FixedIdBaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;

@Table(name = "sys_client_sms")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class ClientSMS extends FixedIdBaseDomain<Long> {
    private String clientId;
    private String provider;
    private String appId;
    private String templateId;
}
