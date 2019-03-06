package com.springcloud.service.auth.security.spi.sms;

import com.springcloud.service.auth.domain.User;
import com.springcloud.service.auth.error.AuthError;
import com.springcloud.service.auth.security.spi.support.UserConverter;
import com.springcloud.service.auth.service.base.IUserService;
import com.springcloud.service.auth.util.AuthParamUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

public class SmsUserDetailsService implements UserDetailsService {

    private IUserService userService;

    private UserConverter userConverter;

    public SmsUserDetailsService(IUserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userType = AuthParamUtils.getUserType();
        User user = userService.findUserByPhone(username, userType, false);
        if (user == null) {
            boolean smsIsCreate = AuthParamUtils.smsIsCreate();
            if (smsIsCreate) {
                user = userService.createUserByPhone(username, userType, AuthParamUtils.getChannel());
            }
        }
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(AuthError.USER_NOT_EXISTS.getMsg());
        }
        return userConverter.convert(user);

    }
}
