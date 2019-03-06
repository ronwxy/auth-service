package com.springcloud.service.auth.service.base.impl;

import com.springboot.autoconfig.tkmapper.service.BaseService;
import com.springcloud.service.auth.domain.Organization;
import com.springcloud.service.auth.service.base.IOrganizationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liubo
 * @since 2018-04-03
 */
@Service
public class OrganizationServiceImpl extends BaseService<Long, Organization> implements IOrganizationService {

}
