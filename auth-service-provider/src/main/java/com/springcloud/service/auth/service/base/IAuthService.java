package com.springcloud.service.auth.service.base;

import com.springboot.autoconfig.tkmapper.service.IService;
import com.springcloud.service.auth.domain.Auth;
import com.springcloud.service.auth.domain.Role;
import com.springcloud.service.auth.domain.User;
import com.springcloud.service.auth.util.GroupTypeEnum;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liubo
 * @since 2018-04-03
 */
public interface IAuthService extends IService<Long, Auth> {

    /**
     * find all roleIds by user;
     *
     * @param userId
     * @return
     * @see #findRoleIdsAuthorized2User(Long)
     * @see #findRoleIdsAuthorized2Job(Long)
     * @see #findRoleIdsAuthorized2Group(Long, GroupTypeEnum)
     */
    Set<Long> findAllRoleIdsByUser(Long userId);

    List<Role> findAllRolesByUser(Long userId);

    Set<Long> findRoleIdsAuthorized2User(Long userId);

    Set<Long> findRoleIdsAuthorized2Job(Long userId);

    Set<Long> extractRoleIdSet(List<Auth> authList);

    Set<Long> findRoleIdsAuthorized2Group(Long userId, GroupTypeEnum groupType);

    Auth findAuthAuthorized2User(Long userId);

    List<User> findUsersByRoles(Collection<Role> roles, boolean and);

    List<User> findUsersByRoleNames(Collection<String> roleNames, boolean and);

    List<User> findUsersByRoleIds(Collection<Long> roleIds, boolean and);
}
