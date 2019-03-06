package com.springcloud.service.auth.security.sms.impl;

import com.springcloud.api.notify.api.SMSClient;
import com.springcloud.api.notify.dto.SMSProvider;
import com.springcloud.api.notify.dto.SMSSendCodeDto;
import com.springcloud.api.notify.dto.SMSVerifyCodeDto;
import com.springcloud.service.auth.domain.ClientSMS;
import com.springcloud.service.auth.security.InvalidClientSMSException;
import com.springcloud.service.auth.security.InvalidSmsCodeException;
import com.springcloud.service.auth.security.sms.ISMSCodeService;
import com.springcloud.service.auth.service.base.IClientSMSService;
import com.springcloud.service.auth.util.RegexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"default", "test", "formal"})
@Service
public class RonglianyunSMSService implements ISMSCodeService {

    private SMSClient smsClient;
    private IClientSMSService clientSMSService;

    @Autowired
    public void setSmsClient(SMSClient smsClient) {
        this.smsClient = smsClient;
    }

    @Autowired
    public void setClientSMSService(IClientSMSService clientSMSService) {
        this.clientSMSService = clientSMSService;
    }

    @Override
    public String sendSMSCode(String clientId, String phone) {
        boolean validPhone = RegexUtils.isChinaMobilePhone(phone);
        if (!validPhone) {
            throw new InvalidSmsCodeException();
        }
        ClientSMS clientSMS = clientSMSService.findByClientId(clientId);
        if (clientSMS == null) {
            throw new InvalidClientSMSException();
        }
        smsClient.sendCode((SMSSendCodeDto) new SMSSendCodeDto().setPhone(phone).setTemplateId(
                clientSMS.getTemplateId()
        ).setAppId(clientSMS.getAppId()).setProvider(SMSProvider.valueOf(clientSMS.getProvider())));
        return null;
    }

    @Override
    public void verifySMSCode(String clientId, String phone, String smsCode) {
        ClientSMS clientSMS = clientSMSService.findByClientId(clientId);
        if (clientSMS == null) {
            throw new InvalidClientSMSException();
        }
        smsClient.verifyCode(new SMSVerifyCodeDto().setAppId(
                clientSMS.getAppId()
        ).setPhone(phone).setCode(smsCode).setProvider(SMSProvider.valueOf(clientSMS.getProvider())));
    }
}
