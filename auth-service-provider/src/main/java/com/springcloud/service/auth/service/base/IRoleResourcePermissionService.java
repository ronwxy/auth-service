package com.springcloud.service.auth.service.base;

import com.springboot.autoconfig.tkmapper.service.IService;
import com.springcloud.service.auth.domain.Resource;
import com.springcloud.service.auth.domain.Role;
import com.springcloud.service.auth.domain.RoleResourcePermission;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 */

public interface IRoleResourcePermissionService extends IService<Long, RoleResourcePermission> {


    List<RoleResourcePermission> findAuthoritiesByResource(Resource resource);

    List<RoleResourcePermission> findAuthoritiesByResourceId(Long resourceId);

    List<RoleResourcePermission> findAuthoritiesByResourceIds(Collection<Long> resourceIds);

    List<RoleResourcePermission> findAuthoritiesByResources(Collection<Resource> resources);

    /**
     * @param role
     * @return list maybe empty
     */
    List<RoleResourcePermission> findAuthoritiesByRole(Role role);

    /**
     * @param roles
     * @return {@link List< RoleResourcePermission >} or {@link Collections#emptyList()}
     */
    List<RoleResourcePermission> findAuthoritiesByRoles(Collection<Role> roles);

    /**
     * @param roleId,means {@link Role#id} or {@link RoleResourcePermission#roleId}
     * @return list maybe empty
     */
    List<RoleResourcePermission> findAuthoritiesByRoleId(Long roleId);

    /**
     * @param roleIds
     * @return {@link List< RoleResourcePermission >} or {@link Collections#emptyList()}
     */
    List<RoleResourcePermission> findByRoleIds(Collection<Long> roleIds);

    /**
     * @param roleStr, means {@link Role#role}
     * @return list maybe empty
     */
    List<RoleResourcePermission> findAuthoritiesByStringRole(String roleStr);

    /**
     * @param stringRoles
     * @return {@link List< RoleResourcePermission >} or {@link Collections#emptyList()}
     */
    List<RoleResourcePermission> findAuthoritiesByStringRoles(Collection<String> stringRoles);


}
