package com.springcloud.service.auth.web.controller;


import com.springboot.autoconfig.tkmapper.controller.BaseController;
import com.springcloud.api.auth.dto.User2OpenidDto;
import com.springcloud.service.auth.domain.User2Openid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liubo
 * @since 2018-04-03
 */
@Controller
@RequestMapping("/users/openIds")
public class User2OpenidController extends BaseController<Long, User2Openid, User2OpenidDto> {

}

