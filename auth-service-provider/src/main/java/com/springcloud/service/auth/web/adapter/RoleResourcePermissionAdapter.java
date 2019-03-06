package com.springcloud.service.auth.web.adapter;

import com.springboot.autoconfig.tkmapper.controller.BaseAdapter;
import com.springcloud.api.auth.dto.RoleResourcePermissionDto;
import com.springcloud.service.auth.domain.RoleResourcePermission;
import org.mapstruct.Mapper;

@Mapper(componentModel = BaseAdapter.MAPSTRUCT_COMPONENT_MODEL_SPRING)
public interface RoleResourcePermissionAdapter extends BaseAdapter<RoleResourcePermission, RoleResourcePermissionDto> {
}
