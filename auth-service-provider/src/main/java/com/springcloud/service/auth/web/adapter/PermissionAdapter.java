package com.springcloud.service.auth.web.adapter;

import com.springboot.autoconfig.tkmapper.controller.BaseAdapter;
import com.springcloud.api.auth.dto.PermissionDto;
import com.springcloud.service.auth.domain.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = BaseAdapter.MAPSTRUCT_COMPONENT_MODEL_SPRING)
public interface PermissionAdapter extends BaseAdapter<Permission, PermissionDto> {
}
