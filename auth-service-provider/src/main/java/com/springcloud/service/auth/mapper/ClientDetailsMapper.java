package com.springcloud.service.auth.mapper;

import com.springboot.autoconfig.tkmapper.mapper.BaseMapper;
import com.springcloud.service.auth.domain.OAuth2ClientDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDetailsMapper extends BaseMapper<OAuth2ClientDetails> {
}
