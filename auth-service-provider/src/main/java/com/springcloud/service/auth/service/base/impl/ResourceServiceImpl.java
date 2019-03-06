package com.springcloud.service.auth.service.base.impl;

import com.springboot.autoconfig.tkmapper.service.BaseService;
import com.springcloud.service.auth.domain.Resource;
import com.springcloud.service.auth.service.base.IResourceService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
public class ResourceServiceImpl extends BaseService<Long, Resource> implements IResourceService {

    @Override
    public List<Resource> LoadByIds(Collection<Long> ids, boolean isShow) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        Example example = new Example(Resource.class);
        example.createCriteria().andEqualTo("isShow", isShow).andIn("id", ids);
        return selectByExample(example);
    }

    @Override
    public List<Resource> LoadByIds(Collection<Long> id) {
        return LoadByIds(id, true);
    }

    @Override
    public Resource loadByIdentity(String identity, boolean isShow) {
        if (StringUtils.isBlank(identity)) {
            return null;
        }
        Example example = new Example(Resource.class);
        example.createCriteria().andEqualTo("identity", identity)
                .andEqualTo("isShow", isShow);
        return selectUniqueByExample(example);

    }

    @Override
    public Resource loadByIdentity(String identity) {
        return loadByIdentity(identity, true);
    }

    @Override
    public Resource loadById(Long id, boolean isShow) {
        Example example = new Example(Resource.class);
        example.createCriteria().andEqualTo("id", id).andEqualTo("isShow", isShow);
        return selectUniqueByExample(example);
    }

    @Override
    public Resource loadById(Long id) {
        return loadById(id, true);
    }

    @Override
    public List<Resource> loadTreeByIdentity(String identity, boolean isShow) {
        Resource r = loadByIdentity(identity, isShow);
        return loadTreeByResource(r, isShow);
    }

    @Override
    public List<Resource> loadTreeByIdentity(String identity) {
        return loadTreeByIdentity(identity, true);
    }

    @Override
    public List<Resource> loadTreeById(Long id, boolean isShow) {
        Resource r = loadById(id, isShow);
        return loadTreeByResource(r, isShow);
    }

    @Override
    public List<Resource> loadTreeById(Long id) {
        return loadTreeById(id, true);
    }

    @Override
    public List<Resource> loadTreeByResource(Resource root, boolean isShow) {
        Example example = new Example(Resource.class);
        example.and().andEqualTo("isShow", isShow);
        example.and()
                .orEqualTo("id", root.getId())
                .orLike("parentIds", root.getChildParentIdsPrefix() + "%");
        return selectByExample(example);
    }

    @Override
    public List<Resource> loadTreeByResource(Resource root) {
        return loadTreeByResource(root, true);
    }
}