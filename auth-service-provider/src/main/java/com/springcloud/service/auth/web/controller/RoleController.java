package com.springcloud.service.auth.web.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.autoconfig.error.exception.ExceptionUtil;
import com.springboot.autoconfig.tkmapper.controller.BaseController;
import com.springcloud.api.auth.dto.RoleDto;
import com.springcloud.service.auth.domain.Role;
import com.springcloud.service.auth.error.AuthError;
import com.springcloud.service.auth.service.base.IRoleService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liubo
 * @since 2018-04-03
 */
@RestController
@RequestMapping("roles")
public class RoleController extends BaseController<Long, Role, RoleDto> {

    private final IRoleService roleService;
    private final ObjectMapper objectMapper;

    @Autowired
    public RoleController(IRoleService roleService, ObjectMapper objectMapper) {
        this.roleService = roleService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("batch/roles")
    public List<RoleDto> findRolesByIds(@RequestParam("ids") List<Long> roleIds) {
        return objectMapper.convertValue(roleService.findRolesByIds(roleIds), new TypeReference<List<RoleDto>>() {
        });
    }

    @GetMapping("{identity}")
    public RoleDto findRoleByIdentity(@PathVariable("identity") String identity) {
        Role role;
        if (NumberUtils.isDigits(identity)) {
            role = roleService.selectByPk(Long.valueOf(identity));
        } else {
            role = roleService.findRoleByStringRole(identity);
        }
        if (Objects.isNull(role)) {
            ExceptionUtil.rethrowClientSideException(AuthError.ROLE_NOT_EXISTS);
        }
        return objectMapper.convertValue(role, RoleDto.class);
    }


}

