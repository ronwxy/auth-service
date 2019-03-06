package com.springcloud.service.auth.security.sms.impl;

import com.springboot.autoconfig.error.exception.UnauthorizedException;
import com.springcloud.service.auth.error.AuthError;
import com.springcloud.service.auth.security.InvalidSmsCodeException;
import com.springcloud.service.auth.security.sms.ISMSCodeService;
import com.springcloud.service.auth.util.RegexUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Profile({"local", "dev"})
@Component
public class InMemorySMSService implements ISMSCodeService {
    private static final String DEV_CODE = "666666";

    @Override
    public String sendSMSCode(String clientId, String phone) {
        boolean validPhone = RegexUtils.isChinaMobilePhone(phone);
        if (!validPhone) {
            throw new InvalidSmsCodeException(AuthError.INVALID_PHONE.getMsg());
        }
        return DEV_CODE;
    }

    @Override
    public void verifySMSCode(String clientId, String phone, String smsCode) {
        if (!Objects.equals(smsCode, DEV_CODE)) {
            throw new UnauthorizedException(AuthError.ERROR_SMS_CODE);
        }
    }
}
