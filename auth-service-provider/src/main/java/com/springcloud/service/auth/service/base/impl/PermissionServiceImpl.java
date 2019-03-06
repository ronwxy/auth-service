package com.springcloud.service.auth.service.base.impl;

import com.springboot.autoconfig.tkmapper.service.BaseService;
import com.springcloud.service.auth.domain.Permission;
import com.springcloud.service.auth.service.base.IPermissionService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
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
public class PermissionServiceImpl extends BaseService<Long, Permission> implements IPermissionService {

    @Override
    public List<Permission> findByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        Example example = new Example(Permission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow", true);
        criteria.andIn("id", ids);
        return selectByExample(example);
    }
}
