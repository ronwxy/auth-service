package com.springcloud.service.auth.web.adapter;

import com.springboot.autoconfig.tkmapper.controller.BaseAdapter;
import com.springcloud.api.auth.dto.GroupRelationDto;
import com.springcloud.service.auth.domain.GroupRelation;
import org.mapstruct.Mapper;

@Mapper(componentModel = BaseAdapter.MAPSTRUCT_COMPONENT_MODEL_SPRING)
public interface GroupRelationAdapter extends BaseAdapter<GroupRelation, GroupRelationDto> {
}
