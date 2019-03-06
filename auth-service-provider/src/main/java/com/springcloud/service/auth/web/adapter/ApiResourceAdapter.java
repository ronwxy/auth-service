package com.springcloud.service.auth.web.adapter;

import com.springboot.autoconfig.tkmapper.controller.BaseAdapter;
import com.springcloud.api.auth.dto.ApiResourceDto;
import com.springcloud.service.auth.service.biz.vo.ApiResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = BaseAdapter.MAPSTRUCT_COMPONENT_MODEL_SPRING)
public interface ApiResourceAdapter extends BaseAdapter<ApiResource, ApiResourceDto> {
}
