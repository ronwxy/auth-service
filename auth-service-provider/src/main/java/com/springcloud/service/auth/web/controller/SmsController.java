package com.springcloud.service.auth.web.controller;

import com.springcloud.api.auth.dto.SendCodeDto;
import com.springcloud.api.auth.dto.VerifyCodeDto;
import com.springcloud.api.notify.dto.BasicSMSDto;
import com.springcloud.service.auth.security.sms.ISMSCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("sms/code")
public class SmsController {
    @Autowired
    private ISMSCodeService ismsCodeService;

    @PostMapping("send")
    public Map<Object, Object> smsCode(@RequestBody SendCodeDto sendCodeDto) {
        String code = ismsCodeService.sendSMSCode(
                sendCodeDto.getAppId(),
                sendCodeDto.getPhone());
        return Collections.singletonMap(BasicSMSDto.SMS_CODE, code);
    }

    @PostMapping("verify")
    public Map<Object, Object> verify(@RequestBody VerifyCodeDto verifyCodeDto) {
        ismsCodeService.verifySMSCode(verifyCodeDto.getAppId(), verifyCodeDto.getPhone(),
                verifyCodeDto.getCode());
        return Collections.emptyMap();
    }
}
