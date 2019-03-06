package com.springcloud.service.auth.security.sms;

/**
 * for sms code send/verify
 *
 * @author liubo
 */
public interface ISMSCodeService {

    String sendSMSCode(String clientId, String phone);

    void verifySMSCode(String clientId, String phone, String smsCode);
}
