package com.springcloud.service.auth.web.adapter;

import com.springboot.autoconfig.tkmapper.controller.BaseAdapter;
import com.springcloud.api.auth.dto.UserDto;
import com.springcloud.service.auth.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = BaseAdapter.MAPSTRUCT_COMPONENT_MODEL_SPRING)
public interface UserAdapter extends BaseAdapter<User, UserDto> {
}
