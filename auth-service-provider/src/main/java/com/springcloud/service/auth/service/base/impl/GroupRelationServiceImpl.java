package com.springcloud.service.auth.service.base.impl;

import com.springboot.autoconfig.tkmapper.service.BaseService;
import com.springcloud.service.auth.domain.GroupRelation;
import com.springcloud.service.auth.service.base.IGroupRelationService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liubo
 * @since 2018-04-03
 */
@Service
public class GroupRelationServiceImpl extends BaseService<Long, GroupRelation> implements IGroupRelationService {

    @Override
    public List<GroupRelation> findByUserId(Long userId) {
        if (userId == null) {
            return Collections.emptyList();
        }
        Example example = new Example(GroupRelation.class);
        example.or().andEqualTo("userId", userId);
        example.or().andGreaterThanOrEqualTo("startUserId", userId)
                .andLessThanOrEqualTo("endUserId", userId);
        return selectByExample(example);
    }

    @Override
    public List<GroupRelation> findByGroupId(Long groupId) {
        if (groupId == null)
            return Collections.emptyList();
        Example example = new Example(GroupRelation.class);
        example.and().andEqualTo("groupId", groupId);
        return selectByExample(example);
    }

    @Override
    public List<GroupRelation> findByGroupIds(Collection<Long> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return Collections.emptyList();
        }
        Example example = new Example(GroupRelation.class);
        example.and().andIn("groupId", groupIds);
        return selectByExample(example);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeGroupRelations(Collection<GroupRelation> groupRelations) {
        if (CollectionUtils.isEmpty(groupRelations)) {
            return;
        }
        groupRelations.forEach(gr -> {
            if (gr.getUserId() == null) {//do nothing;the user is in [startUserId,endUsesrId]
            } else {
                getMapper().delete(gr);
            }
        });
    }
}
