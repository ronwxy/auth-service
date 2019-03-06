package com.springcloud.api.auth.api;

import com.springcloud.api.auth.dto.ApiResourcePermissionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

@FeignClient(name = "auth-service")
public interface ApiAuthClient {

    @RequestMapping(value = "api/auths/authorities", method = RequestMethod.GET)
    Set<ApiResourcePermissionDto> findAllApiAuthorities();
}
