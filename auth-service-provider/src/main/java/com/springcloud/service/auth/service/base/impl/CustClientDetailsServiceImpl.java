package com.springcloud.service.auth.service.base.impl;

import com.springboot.autoconfig.tkmapper.service.BaseService;
import com.springcloud.service.auth.domain.OAuth2ClientDetails;
import com.springcloud.service.auth.service.base.ICustClientDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class CustClientDetailsServiceImpl extends BaseService<Long, OAuth2ClientDetails> implements ICustClientDetailsService {
    @Override
    public OAuth2ClientDetails findClientByClientId(String clientId) {
        if (StringUtils.isBlank(clientId)) {
            return null;
        }
        Example example = new Example(OAuth2ClientDetails.class);
        example.createCriteria().andEqualTo("clientId", clientId);
        return selectOneByExample(example);
    }
}
