package com.springcloud.service.auth.service.biz;

import com.springcloud.service.auth.service.biz.vo.ApiResourcePermission;

import java.util.Set;

public interface IApiResourceBiz {

    Set<ApiResourcePermission> loadAllApiResourcePermissions();

}
