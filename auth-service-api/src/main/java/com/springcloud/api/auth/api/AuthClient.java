package com.springcloud.api.auth.api;

import com.springcloud.api.auth.dto.RoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = BaseClient.AUTH_SERVICE_NAME)
public interface AuthClient {

    @RequestMapping("auths/roles/{id}")
    List<RoleDto> findAllRolesByUserId(@PathVariable("id") Long userId);


}
