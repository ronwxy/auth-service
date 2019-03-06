package com.springcloud.service.auth.web.adapter;

import com.springboot.autoconfig.tkmapper.controller.BaseAdapter;
import com.springcloud.api.auth.dto.ResourceDto;
import com.springcloud.service.auth.domain.Resource;
import org.mapstruct.Mapper;

@Mapper(componentModel = BaseAdapter.MAPSTRUCT_COMPONENT_MODEL_SPRING)
public interface ResourceAdapter extends BaseAdapter<Resource, ResourceDto> {
}
