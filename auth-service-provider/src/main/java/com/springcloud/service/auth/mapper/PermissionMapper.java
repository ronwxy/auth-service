package com.springcloud.service.auth.mapper;

import com.springboot.autoconfig.tkmapper.mapper.BaseMapper;
import com.springcloud.service.auth.domain.Permission;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author liubo
 * @since 2018-04-03
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

}
