package com.springcloud.service.auth.web.adapter;

import com.springboot.autoconfig.tkmapper.controller.BaseAdapter;
import com.springcloud.api.auth.dto.UserOrganizationJobDto;
import com.springcloud.service.auth.domain.UserOrganizationJob;
import org.mapstruct.Mapper;

@Mapper(componentModel = BaseAdapter.MAPSTRUCT_COMPONENT_MODEL_SPRING)
public interface UserOrgJobAdapter extends BaseAdapter<UserOrganizationJob, UserOrganizationJobDto> {
}
