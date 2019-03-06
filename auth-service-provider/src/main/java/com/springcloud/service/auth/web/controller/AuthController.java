package com.springcloud.service.auth.web.controller;


import com.springboot.autoconfig.tkmapper.controller.BaseController;
import com.springboot.autoconfig.web.annotation.CurrentUserId;
import com.springcloud.api.auth.dto.AuthDto;
import com.springcloud.api.auth.dto.RoleDto;
import com.springcloud.service.auth.domain.Auth;
import com.springcloud.service.auth.domain.Role;
import com.springcloud.service.auth.service.base.IRoleService;
import com.springcloud.service.auth.service.biz.IAuthBiz;
import com.springcloud.service.auth.service.biz.vo.StringPermissions;
import com.springcloud.service.auth.web.adapter.RoleAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liubo
 * @since 2018-04-03
 */
@RestController
@RequestMapping("auths")
public class AuthController extends BaseController<Long, Auth, AuthDto> {

    private IAuthBiz authBiz;

    private IRoleService roleService;
    private RoleAdapter roleAdapter;

    @Autowired
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setRoleAdapter(RoleAdapter roleAdapter) {
        this.roleAdapter = roleAdapter;
    }

    @Autowired
    public void setAuthBiz(IAuthBiz authBiz) {
        this.authBiz = authBiz;
    }

    @GetMapping("permissions")
    public StringPermissions findAllStringResources(@CurrentUserId Long userId) {
        return authBiz.findStringPermissionsByUserId(userId);
    }

    @GetMapping("roles/{id}")
    public List<RoleDto> findAllRolesByUserId(@PathVariable("id") Long userId) {
        List<Role> roles = roleService.findRolesByIds(Collections.singleton(userId));
        return (List<RoleDto>) roleAdapter.entityToDto(roles);
    }


}

