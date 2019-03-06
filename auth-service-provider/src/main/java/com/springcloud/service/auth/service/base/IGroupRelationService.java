package com.springcloud.service.auth.service.base;

import com.springboot.autoconfig.tkmapper.service.IService;
import com.springcloud.service.auth.domain.GroupRelation;

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
public interface IGroupRelationService extends IService<Long, GroupRelation> {

    List<GroupRelation> findByUserId(Long userId);

    List<GroupRelation> findByGroupId(Long groupId);

    List<GroupRelation> findByGroupIds(Collection<Long> groupIds);

    void removeGroupRelations(Collection<GroupRelation> groupRelations);
}
