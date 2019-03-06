package com.springcloud.service.auth.web.adapter;

import com.springboot.autoconfig.tkmapper.controller.BaseAdapter;
import com.springcloud.api.auth.dto.ApiResourcePermissionDto;
import com.springcloud.service.auth.service.biz.vo.ApiResourcePermission;
import org.mapstruct.Mapper;

@Mapper(componentModel = BaseAdapter.MAPSTRUCT_COMPONENT_MODEL_SPRING)
public interface ApiResourcePermissionAdapter extends BaseAdapter<ApiResourcePermission, ApiResourcePermissionDto> {

}
