package com.springcloud.service.auth.service.base.impl;

import com.springboot.autoconfig.tkmapper.service.BaseService;
import com.springcloud.service.auth.domain.Resource;
import com.springcloud.service.auth.domain.Role;
import com.springcloud.service.auth.domain.RoleResourcePermission;
import com.springcloud.service.auth.service.base.IRoleResourcePermissionService;
import com.springcloud.service.auth.service.base.IRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liubo
 * @since 2018-04-03
 */
@Service
public class RoleResourcePermissionServiceImpl extends BaseService<Long, RoleResourcePermission> implements IRoleResourcePermissionService {

    private IRoleService roleService;

    @Override
    public List<RoleResourcePermission> findAuthoritiesByResource(Resource resource) {
        return findAuthoritiesByResourceId(resource.getId());
    }

    @Override
    public List<RoleResourcePermission> findAuthoritiesByResourceId(Long resourceId) {
        Example example = new Example(RoleResourcePermission.class);
        example.and().andEqualTo("resourceId", resourceId);
        return selectByExample(example);
    }

    @Override
    public List<RoleResourcePermission> findAuthoritiesByResourceIds(Collection<Long> resourceIds) {
        Example example = new Example(RoleResourcePermission.class);
        if (CollectionUtils.isEmpty(resourceIds)) {
            return Collections.emptyList();
        }
        example.createCriteria()
                .andIn("resourceId", resourceIds);

        return selectByExample(example);
    }

    @Override
    public List<RoleResourcePermission> findAuthoritiesByResources(Collection<Resource> resources) {
        if (CollectionUtils.isEmpty(resources)) {
            return Collections.emptyList();
        }
        Collection<Long> resourceIds = resources.stream().mapToLong(Resource::getId).boxed().collect(Collectors.toSet());
        return findAuthoritiesByResourceIds(resourceIds);
    }


    @Autowired
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public List<RoleResourcePermission> findAuthoritiesByRole(Role role) {
        if (Objects.isNull(role)) {
            return null;
        }
        return findAuthoritiesByRoleId(role.getId());
    }

    @Override
    public List<RoleResourcePermission> findAuthoritiesByRoles(Collection<Role> roles) {
        if (CollectionUtils.isEmpty(roles)) {
            return Collections.emptyList();
        }
        Collection<Long> roleIds = roles.stream().mapToLong(Role::getId).boxed().collect(Collectors.toCollection(HashSet::new));
        return findByRoleIds(roleIds);
    }

    @Override
    public List<RoleResourcePermission> findAuthoritiesByRoleId(Long roleId) {
        if (Objects.isNull(roleId)) {
            return null;
        }
        Example example = new Example(RoleResourcePermission.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        return selectByExample(example);
    }

    @Override
    public List<RoleResourcePermission> findByRoleIds(Collection<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        Example example = new Example(RoleResourcePermission.class);
        example.createCriteria().andIn("roleId", roleIds);
        return selectByExample(example);
    }

    @Override
    public List<RoleResourcePermission> findAuthoritiesByStringRole(String roleStr) {
        if (StringUtils.isBlank(roleStr)) {
            return Collections.emptyList();
        }
        Role role = roleService.findRoleByStringRole(roleStr);
        return findAuthoritiesByRoleId(role.getId());
    }

    @Override
    public List<RoleResourcePermission> findAuthoritiesByStringRoles(Collection<String> stringRoles) {
        if (CollectionUtils.isEmpty(stringRoles)) {
            return Collections.emptyList();
        }
        List<Role> roles = roleService.findRolesByStringRoles(stringRoles);
        if (CollectionUtils.isEmpty(roles)) {
            return Collections.emptyList();
        }
        Collection<Long> roleIds = roles.stream().mapToLong(Role::getId).boxed().collect(Collectors.toCollection(HashSet::new));
        return findByRoleIds(roleIds);
    }
}
