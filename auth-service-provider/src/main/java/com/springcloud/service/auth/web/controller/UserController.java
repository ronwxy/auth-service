package com.springcloud.service.auth.web.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.autoconfig.tkmapper.controller.BaseController;
import com.springboot.autoconfig.web.annotation.CurrentUserId;
import com.springcloud.api.auth.dto.*;
import com.springcloud.service.auth.domain.User;
import com.springcloud.service.auth.service.base.IAuthService;
import com.springcloud.service.auth.service.base.IUserService;
import com.springcloud.service.auth.service.biz.IUserBiz;
import com.springcloud.service.auth.web.adapter.UserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liubo
 * @since 2018-04-03
 */
@RestController
@RequestMapping("users")
public class UserController extends BaseController<Long, User, UserDto> {

    private IUserBiz userBiz;
    private IUserService userService;
    private ObjectMapper objectMapper;
    private IAuthService authService;
    private UserAdapter userAdapter;

    @Autowired
    public void setAuthService(IAuthService authService) {
        this.authService = authService;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserBiz(IUserBiz userBiz) {
        this.userBiz = userBiz;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Long createUser(@RequestBody UserSaveReqDto req, @CurrentUserId Long currentLoginUserId) {
        return userBiz.createOrUpdateUserWithAuthorities(objectMapper.convertValue(req.getUser(), User.class),
                userBiz.findRealJobs(req.getJobIds(), req.getJobNames()),
                userBiz.findRealRoles(req.getRoleIds(), req.getRoleNames()),
                userBiz.findRealGroups(req.getRoleIds(), req.getGroupNames()), req.isJobsEmpty2remove(),
                req.isRolesEmpty2remove(), req.isGroups2remove(), currentLoginUserId);
    }

    @GetMapping("byJobNames")
    public Collection<UserDto> findUsersByJobNames(@RequestParam("jobNames") Collection<String> jobNames) {
        return objectMapper.convertValue(userBiz.queryUsersByJobNames(jobNames),
                new TypeReference<List<UserDto>>() {
                });
    }

    @GetMapping("byJobIds")
    public Collection<UserDto> findUsersByJobIds(@RequestParam("jobIds") Collection<Long> jobIds) {
        return objectMapper.convertValue(userBiz.queryUsersByJobIds(jobIds),
                new TypeReference<List<UserDto>>() {
                }
        );
    }

    @GetMapping("byRoleIds")
    public Collection<UserDto> findUsersByRoleIds(@RequestParam("roleIds") Collection<Long> roleIds
            , @RequestParam(name = "and", defaultValue = "false", required = false) boolean and) {
        return objectMapper.convertValue(authService.findUsersByRoleIds(roleIds, and)
                , new TypeReference<List<UserDto>>() {
                });
    }

    @GetMapping("byRoleNames")
    public Collection<User> findUsersByRoleNames(@RequestParam("roleNames") Collection<String> roleNames
            , @RequestParam(name = "and", defaultValue = "false", required = false) boolean and) {
        return objectMapper.convertValue(authService.findUsersByRoleNames(roleNames, and)
                , new TypeReference<List<UserDto>>() {
                });
    }

    @GetMapping
    public UserDto loadUser(@CurrentUserId Long userId) {
        User user = userService.selectByPk(userId);
        return userAdapter.entityToDto(user);
    }

    @GetMapping("phone/{phone}")
    public UserDto loadUserByPhone(@PathVariable("phone") String phone, @RequestParam String type) {
        User user = userService.findUserByPhone(phone, type, false);
        return userAdapter.entityToDto(user);
    }

    //weixin do not support patch method
    @RequestMapping(value = "changePassword", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public Map<Object,Object> changePassword(@CurrentUserId Long userId,
                              @RequestBody ChangePasswordDto changePasswordDto) {
        User user = userService.selectByPk(userId);
        userService.changePassword(user, changePasswordDto.getNewPassword());
        return Collections.emptyMap();
    }

    //weixin do not support patch method
    @RequestMapping(value = "resetPassword", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public Map<Object,Object> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        userService.resetPassword(resetPasswordDto.getPhone(), resetPasswordDto.getType(),
                resetPasswordDto.getNewPassword());
        return Collections.emptyMap();
    }

    @PostMapping("phoneRegister")
    public UserDto register(@RequestBody RegisterByPhoneDto registerInfo) {
        return userAdapter.entityToDto(userService.createUserByPhone(registerInfo.getPhone(), registerInfo.getType(),
                registerInfo.getChannel(),
                registerInfo.getAppId(), registerInfo.getSmsCode(), registerInfo.getPassword()));
    }

}

