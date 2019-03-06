package com.springcloud.service.auth.service.biz.impl;

import com.springboot.autoconfig.error.exception.ExceptionUtil;
import com.springcloud.service.auth.domain.Permission;
import com.springcloud.service.auth.domain.Resource;
import com.springcloud.service.auth.domain.RoleResourcePermission;
import com.springcloud.service.auth.domain.User;
import com.springcloud.service.auth.error.AuthError;
import com.springcloud.service.auth.service.base.*;
import com.springcloud.service.auth.service.biz.IAuthBiz;
import com.springcloud.service.auth.service.biz.vo.StringPermissions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthBizImpl implements IAuthBiz {
    private IRoleResourcePermissionService roleResourcePermissionService;
    private IAuthService authService;
    private IResourceService resourceService;
    private IUserService userService;
    private IPermissionService permissionService;
    private IRoleService roleService;
    private IUserOrganizationJobService userOrganizationJobService;
    private IJobService jobService;

    @Autowired
    public void setUserOrganizationJobService(IUserOrganizationJobService userOrganizationJobService) {
        this.userOrganizationJobService = userOrganizationJobService;
    }

    @Autowired
    public void setJobService(IJobService jobService) {
        this.jobService = jobService;
    }

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
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAuthService(IAuthService authService) {
        this.authService = authService;
    }

    @Autowired
    public void setRoleResourcePermissionService(IRoleResourcePermissionService roleResourcePermissionService) {
        this.roleResourcePermissionService = roleResourcePermissionService;
    }


    @Override
    public List<RoleResourcePermission> findAllResourcePermissionByUser(User user) {
        Set<Long> roleIds = authService.findAllRoleIdsByUser(user.getId());
        return roleResourcePermissionService.findByRoleIds(roleIds);
    }

    @Override
    public List<RoleResourcePermission> findAllResourcePermissionByIdentity(String identity, String userType) {
        User u = userService.findUserByIdentity(identity, userType, false);
        return findAllResourcePermissionByUser(u);
    }


    @Override
    @Cacheable(cacheNames = "stringPermissions", key = "#identity+'#'+#userType")
    public StringPermissions findStringPermissionsByIdentity(String identity, String userType) {
        User user = userService.findUserByIdentity(identity, userType, false);
        return findStringPermissionsByUser(user);
    }

    @Override
    @Cacheable(cacheNames = "stringPermissions", key = "'permissions#'+#user?.id")
    public StringPermissions findStringPermissionsByUser(User user) {
        List<RoleResourcePermission> tmp = findAllResourcePermissionByUser(user);
        StringPermissions result = new StringPermissions();
        result.setUserId(user.getId());
        //1.find string roles
        Collection<Long> roleIds = tmp.stream().mapToLong(RoleResourcePermission::getRoleId).boxed().collect(Collectors.toCollection(HashSet::new));

        result.setRoles(roleService.findStringRolesByIds(roleIds));
        //2.find string jobs
        Set<Long> jobIds = userOrganizationJobService.findJobIdsByUserId(user.getId());
        result.setJobs(jobService.findStringJobsByIds(jobIds));
        //3.find string resource permission;
        //combine the permissions
        Map<Long, Set<Long>> resourceId2PermissionIdsMap =
                tmp.stream().collect(Collectors.groupingBy(RoleResourcePermission::getResourceId,
                        Collectors.mapping(p ->
                                        Arrays.stream(p.getPermissionIds().split(",")).mapToLong(Long::valueOf)
                                                .boxed().collect(Collectors.toSet()),
                                Collectors.toSet())


                )).//resourceId-set<permissionId>
                        entrySet().stream().collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue().stream().flatMap(
                        s -> s.stream()).collect(Collectors.toSet())));

        //resourceId-identity
        Map<Long, String> resources = resourceService.LoadByIds(resourceId2PermissionIdsMap.keySet(), true)
                .stream().collect(Collectors.toMap(Resource::getId, Resource::getIdentity));
        //permissionId-identity
        Map<Long, String> permissions = permissionService.findByIds(
                resourceId2PermissionIdsMap.values().stream().flatMap(p -> p.stream()).collect(Collectors.toSet())
        ).stream().collect(Collectors.toMap(Permission::getId, Permission::getPermission));

        Set<StringPermissions.StringResourcePermission> stringResourcePermissions = resourceId2PermissionIdsMap.entrySet()
                .stream().map(m -> {
                    StringPermissions.StringResourcePermission srp = new StringPermissions.StringResourcePermission();
                    srp.setResource(resources.get(m.getKey()))
                            .setPermissions(permissions.entrySet().stream().filter(p -> m.getValue().contains(p.getKey()))
                                    .map(Map.Entry::getValue).collect(Collectors.toSet()));
                    return srp;
                }).filter(s -> StringUtils.isNotBlank(s.getResource())).collect(Collectors.toSet());
        result.setResourcePermissions(stringResourcePermissions);

        return result;
    }

    @Override
    @Cacheable(cacheNames = "stringPermissions", key = "'permissions#'+#userId")
    public StringPermissions findStringPermissionsByUserId(Long userId) {
        User user = userService.selectByPk(userId);
        if (user == null) {
            ExceptionUtil.rethrowServerSideException(AuthError.USER_NOT_EXISTS);
        }
        return findStringPermissionsByUser(user);
    }
}
