package com.springcloud.service.auth.service.biz;

import com.springcloud.service.auth.domain.RoleResourcePermission;
import com.springcloud.service.auth.domain.User;
import com.springcloud.service.auth.service.biz.vo.StringPermissions;

import java.util.List;

/**
 * used for business operation
 *
 * @author liubo
 */
public interface IAuthBiz {

    /**
     * @param user
     * @return resource-permissions
     */
    List<RoleResourcePermission> findAllResourcePermissionByUser(User user);

    List<RoleResourcePermission> findAllResourcePermissionByIdentity(String identity, String userType);

    StringPermissions findStringPermissionsByUser(User user);

    StringPermissions findStringPermissionsByIdentity(String identity, String userType);

    StringPermissions findStringPermissionsByUserId(Long userId);

}
