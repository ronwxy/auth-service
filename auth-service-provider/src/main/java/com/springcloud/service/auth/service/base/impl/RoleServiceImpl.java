package com.springcloud.service.auth.service.base.impl;

import com.springboot.autoconfig.tkmapper.service.BaseService;
import com.springcloud.service.auth.domain.Role;
import com.springcloud.service.auth.service.base.IRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
public class RoleServiceImpl extends BaseService<Long, Role> implements IRoleService {

    @Override
    public List<Role> findRolesByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return selectByPks(ids);
    }

    @Override
    public Set<String> findStringRolesByIds(Collection<Long> ids) {
        List<Role> roles = findRolesByIds(ids);
        return roles.stream().map(Role::getRole).collect(Collectors.toSet());
    }

    @Override
    public Role findRoleByStringRole(String stringRole) {
        if (StringUtils.isBlank(stringRole)) {
            return null;
        }
        Example example = new Example(Role.class);
        example.createCriteria().andEqualTo("role", stringRole);
        return selectOneByExample(example);
    }

    @Override
    public List<Role> findRolesByStringRoles(Collection<String> stringRoles) {
        if (CollectionUtils.isEmpty(stringRoles)) {
            return Collections.emptyList();
        }
        Example example = new Example(Role.class);
        example.createCriteria().andIn("role", stringRoles);
        return selectByExample(example);
    }
}
