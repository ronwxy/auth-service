package com.springcloud.service.auth.web.controller;

import com.springboot.autoconfig.tkmapper.controller.BaseController;
import com.springcloud.api.auth.dto.ClientDetailsDto;
import com.springcloud.service.auth.domain.OAuth2ClientDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("oauth2Clients")
public class ClientDetailsController extends BaseController<Long, OAuth2ClientDetails, ClientDetailsDto> {

}
