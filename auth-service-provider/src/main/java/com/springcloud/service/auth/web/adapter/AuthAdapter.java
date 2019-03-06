package com.springcloud.service.auth.web.adapter;

import com.springboot.autoconfig.tkmapper.controller.BaseAdapter;
import com.springcloud.api.auth.dto.AuthDto;
import com.springcloud.service.auth.domain.Auth;
import com.springcloud.service.auth.util.Constants;
import org.mapstruct.Mapper;


@Mapper(componentModel = Constants.MAPSTRUCT_COMPONENT_MODEL_SPRING)
public interface AuthAdapter extends BaseAdapter<Auth, AuthDto> {

}
