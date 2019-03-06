package com.springcloud.service.auth.security.spi.support;

import com.google.common.collect.Lists;
import com.springcloud.autoconfig.security.MyUserDetails;
import com.springcloud.service.auth.domain.Role;
import com.springcloud.service.auth.domain.User;
import com.springcloud.service.auth.security.lock.ILockManager;
import com.springcloud.service.auth.service.base.IAuthService;
import com.springcloud.service.auth.service.base.IRoleService;
import com.springcloud.service.auth.util.UserStatusEnum;
import com.springcloud.service.auth.util.UserTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * convert {@link User} to {@link UserDetails}
 *
 * @author liubo
 */
public class UserConverter implements Converter<User, UserDetails> {

    private static final Logger logger = LoggerFactory.getLogger(UserConverter.class);
    private IAuthService authService;
    private IRoleService roleService;
    private ILockManager lockManager;

    public UserConverter(IAuthService authService, IRoleService roleService, ILockManager lockManager) {
        this.authService = authService;
        this.roleService = roleService;
        this.lockManager = lockManager;
    }

    @Override
    public UserDetails convert(User user) {

        List<Role> roles = Collections.emptyList();
        if (!UserTypeEnum.m.name().equals(user.getType()) && !UserTypeEnum.d.name().equals(user.getType())) {
            Set<Long> roleIds = authService.findAllRoleIdsByUser(user.getId());
            if (CollectionUtils.isEmpty(roleIds)) {
                logger.warn("the user:{} has configured no roles,configured roles first", user.getUsername());
                //must define an error for no role configuration;
            } else {
                roles = roleService.findRolesByIds(Lists.newArrayList(roleIds));
            }
        }

        MyUserDetails.UserBuilder builder = MyUserDetails.newBuilder(user.getId())
                .username(user.getUsername())
                .phone(user.getPhone())
                .email(user.getEmail())
                .type(user.getType())
                .password(user.getPassword())
                .salt(user.getSalt());
        if (Objects.equals(UserStatusEnum.blocked.name(), user.getStatus())) {
            builder.disabled(true);
        }
        if (lockManager.isLocked(user)) {
            builder.accountLocked(true);
        }
        builder.roles(roles.stream().map(role -> String.valueOf(role.getRole())).toArray(String[]::new));
        return builder.build();
    }
}
