package com.springcloud.api.auth.api;

import com.springcloud.api.auth.dto.RoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = BaseClient.AUTH_SERVICE_NAME)
@RequestMapping("roles")
public interface RoleClient extends BaseClient<Long, RoleDto, RoleDto> {
}
