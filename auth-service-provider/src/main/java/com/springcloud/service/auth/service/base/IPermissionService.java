package com.springcloud.service.auth.service.base;

import com.springboot.autoconfig.tkmapper.service.IService;
import com.springcloud.service.auth.domain.Permission;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 */
public interface IPermissionService extends IService<Long, Permission> {

    List<Permission> findByIds(Collection<Long> ids);
}
