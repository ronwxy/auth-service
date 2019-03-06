package com.springcloud.service.auth.web.controller;

import com.springcloud.api.auth.api.ApiAuthClient;
import com.springcloud.api.auth.dto.ApiResourcePermissionDto;
import com.springcloud.service.auth.service.biz.IApiResourceBiz;
import com.springcloud.service.auth.web.adapter.ApiResourcePermissionAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("api/auths")
public class ApiAuthController implements ApiAuthClient {
    private IApiResourceBiz apiResourceBiz;
    private ApiResourcePermissionAdapter apiResourcePermissionAdapter;

    @Autowired
    public void setApiResourcePermissionAdapter(ApiResourcePermissionAdapter apiResourcePermissionAdapter) {
        this.apiResourcePermissionAdapter = apiResourcePermissionAdapter;
    }

    @Autowired
    public void setApiResourceBiz(IApiResourceBiz apiResourceBiz) {
        this.apiResourceBiz = apiResourceBiz;
    }

    @GetMapping("authorities")
    public Set<ApiResourcePermissionDto> findAllApiAuthorities() {
        return (Set<ApiResourcePermissionDto>) apiResourcePermissionAdapter.entityToDto(apiResourceBiz.loadAllApiResourcePermissions());
    }
}
