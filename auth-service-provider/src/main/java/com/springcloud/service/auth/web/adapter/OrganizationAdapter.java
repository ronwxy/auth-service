package com.springcloud.service.auth.web.adapter;

import com.springboot.autoconfig.tkmapper.controller.BaseAdapter;
import com.springcloud.api.auth.dto.OrganizationDto;
import com.springcloud.service.auth.domain.Organization;
import org.mapstruct.Mapper;

@Mapper(componentModel = BaseAdapter.MAPSTRUCT_COMPONENT_MODEL_SPRING)
public interface OrganizationAdapter extends BaseAdapter<Organization, OrganizationDto> {

}
