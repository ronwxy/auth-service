package com.springcloud.service.auth.service.base;

import com.springboot.autoconfig.tkmapper.service.IService;
import com.springcloud.service.auth.domain.Resource;

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
public interface IResourceService extends IService<Long, Resource> {

    List<Resource> LoadByIds(Collection<Long> ids, boolean isShow);

    List<Resource> LoadByIds(Collection<Long> id);

    Resource loadByIdentity(String identity, boolean isShow);

    Resource loadByIdentity(String identity);

    Resource loadById(Long id, boolean isShow);

    Resource loadById(Long id);

    List<Resource> loadTreeByIdentity(String identity, boolean isShow);

    List<Resource> loadTreeByIdentity(String identity);

    List<Resource> loadTreeById(Long id, boolean isShow);

    List<Resource> loadTreeById(Long id);

    List<Resource> loadTreeByResource(Resource root, boolean isShow);

    List<Resource> loadTreeByResource(Resource root);
}
