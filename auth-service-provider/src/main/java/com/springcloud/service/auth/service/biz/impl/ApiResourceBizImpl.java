package com.springcloud.service.auth.service.biz.impl;

import com.springcloud.service.auth.domain.Resource;
import com.springcloud.service.auth.domain.Role;
import com.springcloud.service.auth.domain.RoleResourcePermission;
import com.springcloud.service.auth.service.base.IPermissionService;
import com.springcloud.service.auth.service.base.IResourceService;
import com.springcloud.service.auth.service.base.IRoleResourcePermissionService;
import com.springcloud.service.auth.service.base.IRoleService;
import com.springcloud.service.auth.service.biz.IApiResourceBiz;
import com.springcloud.service.auth.service.biz.vo.ApiResource;
import com.springcloud.service.auth.service.biz.vo.ApiResourcePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ApiResourceBizImpl implements IApiResourceBiz {

    private IResourceService resourceService;
    private IRoleService roleService;
    private IPermissionService permissionService;
    private IRoleResourcePermissionService roleResourcePermissionService;

    @Autowired
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPermissionService(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Autowired
    public void setResourceService(IResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @Autowired
    public void setRoleResourcePermissionService(IRoleResourcePermissionService roleResourcePermissionService) {
        this.roleResourcePermissionService = roleResourcePermissionService;
    }

    @Cacheable(cacheNames = "api-authorities", key = "'all-api-authorities'")
    @Override
    public Set<ApiResourcePermission> loadAllApiResourcePermissions() {
        Resource apiRootResource = resourceService.loadByIdentity(Resource.API_ROOT_IDENTITY);
        List<Resource> candidate = resourceService.loadTreeByResource(apiRootResource);
        List<RoleResourcePermission> candidateRelations = roleResourcePermissionService.
                findAuthoritiesByResources(candidate);
        Map<Long, List<RoleResourcePermission>> candidateRelationsMap =
                candidateRelations.stream().collect(Collectors.groupingBy(RoleResourcePermission::getResourceId));

        Map<Long, Role> candidateRoleMap =
                roleService.findRolesByIds(
                        candidateRelations.stream().map(RoleResourcePermission::getRoleId).
                                collect(Collectors.toSet()))
                        .stream().collect(Collectors.toMap(Role::getId, Function.identity()));
        Set<ApiResourcePermission> result = new HashSet<>();


        candidate.forEach(resource -> {
            ApiResource ar = new ApiResource(resource.getId(), resource.getIdentity(), resource.getName(), resource.getParentId(),
                    resource.getParentIds(), resource.getUrl(), resource.getHttpMethod());
            List<RoleResourcePermission> relations = candidateRelationsMap.getOrDefault(resource.getId(),
                    Collections.emptyList());
            Set<Long> roleIds = relations.stream().mapToLong(RoleResourcePermission::getRoleId)
                    .boxed().collect(Collectors.toSet());
            Set<String> roles = candidateRoleMap.entrySet().stream().filter(entry -> roleIds.contains(entry.getKey()))
                    .map(entry -> entry.getValue().getRole()).collect(Collectors.toSet());
            result.add(new ApiResourcePermission(ar, roles));

        });
        return result;
    }


}
