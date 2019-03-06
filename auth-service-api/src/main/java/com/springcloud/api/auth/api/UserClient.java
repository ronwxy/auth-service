package com.springcloud.api.auth.api;

import com.springcloud.api.auth.dto.UserDto;
import com.springcloud.api.auth.dto.UserSaveReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@FeignClient(name = BaseClient.AUTH_SERVICE_NAME)
public interface UserClient {
    /**
     * this method only support authorize model and depends on oauth2.feign.enabled=true
     * internal feign invoke without authorize is not support;
     *
     * @return
     */
    @RequestMapping(value = "users", method = RequestMethod.GET)
    UserDto loadUser();

    @RequestMapping(value = "users/{id}", method = RequestMethod.GET)
    UserDto findById(@PathVariable("id") Long id);

    @RequestMapping(value = "users", method = RequestMethod.POST)
    Long saveUser(@RequestBody UserSaveReqDto userInfo);

    @RequestMapping(value = "users/byJobNames", method = RequestMethod.GET)
    Collection<UserDto> queryUsersByJobNames(@RequestParam("jobNames") Collection<String> jobNames);

    @RequestMapping(value = "users/byJobIds", method = RequestMethod.GET)
    Collection<UserDto> queryUsersByJobIds(@RequestParam("jobIds") Collection<Long> jobIds);

    @RequestMapping(value = "users/byRoleIds", method = RequestMethod.GET)
    Collection<UserDto> findUsersByRoleIds(@RequestParam("roleIds") Collection<Long> roleIds
            , @RequestParam(name = "and") boolean and);

    @RequestMapping(value = "users/byRoleNames", method = RequestMethod.GET)
    Collection<UserDto> findUsersByRoleNames(@RequestParam("roleNames") Collection<String> roleNames
            , @RequestParam(name = "and") boolean and);

    @RequestMapping(value = "users/phone/{phone}", method = RequestMethod.GET)
    UserDto loadUserByPhone(@PathVariable("phone") String phone, @RequestParam("type") String type);
}
