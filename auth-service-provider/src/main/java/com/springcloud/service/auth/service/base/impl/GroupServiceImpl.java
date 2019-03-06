package com.springcloud.service.auth.service.base.impl;

import com.springboot.autoconfig.tkmapper.service.BaseService;
import com.springcloud.service.auth.domain.Group;
import com.springcloud.service.auth.service.base.IGroupService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

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
public class GroupServiceImpl extends BaseService<Long, Group> implements IGroupService {


    @Override
    public List<Group> findGroupsByNames(Collection<String> groupNames) {
        if (CollectionUtils.isEmpty(groupNames)) {
            return Collections.emptyList();
        }
        Example example = new Example(Group.class);
        example.and().andIn("name", groupNames);
        return selectByExample(example);
    }

    @Override
    public List<Group> findGroupsByIds(Collection<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return Collections.emptyList();
        }
        Example example = new Example(Group.class);
        example.and().andIn("id",ids);
        return selectByExample(example);
    }

    @Override
    public Group findByName(String groupName) {
        if (StringUtil.isEmpty(groupName)) {
            return null;
        }
        Example example = new Example(Group.class);
        example.and().andEqualTo("name", groupName);
        return selectUniqueByExample(example);
    }
}
