package com.springcloud.service.auth.web.adapter;

import com.springboot.autoconfig.tkmapper.controller.BaseAdapter;
import com.springcloud.api.auth.dto.ClientDetailsDto;
import com.springcloud.service.auth.domain.OAuth2ClientDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = BaseAdapter.MAPSTRUCT_COMPONENT_MODEL_SPRING)
public interface ClientDetailsAdapter extends BaseAdapter<OAuth2ClientDetails, ClientDetailsDto> {
}
