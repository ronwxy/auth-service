package com.springcloud.service.auth.web.controller;


import com.springboot.autoconfig.tkmapper.controller.BaseController;
import com.springcloud.api.auth.dto.GroupRelationDto;
import com.springcloud.service.auth.domain.GroupRelation;
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
@RequestMapping("/groupRelations")
public class GroupRelationController extends BaseController<Long, GroupRelation, GroupRelationDto> {

}

