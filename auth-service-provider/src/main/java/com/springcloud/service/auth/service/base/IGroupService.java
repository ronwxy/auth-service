package com.springcloud.service.auth.service.base;

import com.springboot.autoconfig.tkmapper.service.IService;
import com.springcloud.service.auth.domain.Group;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liubo
 * @since 2018-04-03
 */
public interface IGroupService extends IService<Long, Group> {

    List<Group> findGroupsByNames(Collection<String> groupNames);
    List<Group> findGroupsByIds(Collection<Long> ids);

    Group findByName(String groupName);
}
