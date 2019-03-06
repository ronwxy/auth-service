package com.springcloud.service.auth.service.base.impl;

import com.springboot.autoconfig.tkmapper.service.BaseService;
import com.springcloud.service.auth.domain.UserStatusHistory;
import com.springcloud.service.auth.service.base.IUserStatusHistoryService;
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
public class UserStatusHistoryServiceImpl extends BaseService<Long, UserStatusHistory> implements IUserStatusHistoryService {

}
