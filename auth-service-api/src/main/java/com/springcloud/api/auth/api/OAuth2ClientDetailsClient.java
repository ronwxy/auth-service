package com.springcloud.api.auth.api;

import com.springcloud.api.auth.dto.ClientDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(BaseClient.AUTH_SERVICE_NAME)
public interface OAuth2ClientDetailsClient {
    /**
     *
     * @param condition，{@link ClientDetailsDto}'s properties;
     * @return
     */
    @RequestMapping(value = "oauth2Clients/one", method = RequestMethod.GET)
    ClientDetailsDto one(@RequestParam Map<String, Object> condition);
}
