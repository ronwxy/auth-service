package com.springcloud.api.auth.api;

import com.springcloud.api.auth.dto.SendCodeDto;
import com.springcloud.api.auth.dto.VerifyCodeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(BaseClient.AUTH_SERVICE_NAME)
public interface PhoneCodeClient {
    @RequestMapping(value = "sms/code/send", method = RequestMethod.POST)
    Map<Object, Object> smsCode(@RequestBody SendCodeDto sendCodeDto);

    @RequestMapping(value = "sms/code/verify", method = RequestMethod.POST)
    void verify(@RequestBody VerifyCodeDto verifyCodeDto);
}
