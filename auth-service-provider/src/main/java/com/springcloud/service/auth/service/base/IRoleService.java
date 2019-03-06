package com.springcloud.service.auth.service.base;

import com.springboot.autoconfig.tkmapper.service.IService;
import com.springcloud.service.auth.domain.Role;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 */
public interface IRoleService extends IService<Long, Role> {

    List<Role> findRolesByIds(Collection<Long> ids);

    Set<String> findStringRolesByIds(Collection<Long> ids);

    Role findRoleByStringRole(String stringRole);

    List<Role> findRolesByStringRoles(Collection<String> stringRoles);
}
