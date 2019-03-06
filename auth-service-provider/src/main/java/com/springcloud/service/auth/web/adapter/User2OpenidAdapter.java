package com.springcloud.service.auth.web.adapter;

import com.springboot.autoconfig.tkmapper.controller.BaseAdapter;
import com.springcloud.api.auth.dto.User2OpenidDto;
import com.springcloud.service.auth.domain.User2Openid;
import org.mapstruct.Mapper;

@Mapper(componentModel = BaseAdapter.MAPSTRUCT_COMPONENT_MODEL_SPRING)
public interface User2OpenidAdapter extends BaseAdapter<User2Openid, User2OpenidDto> {
}
