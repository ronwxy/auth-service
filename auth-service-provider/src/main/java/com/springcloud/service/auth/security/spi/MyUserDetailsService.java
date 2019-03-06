package com.springcloud.service.auth.security.spi;

import com.springcloud.service.auth.domain.User;
import com.springcloud.service.auth.error.AuthError;
import com.springcloud.service.auth.security.spi.support.UserConverter;
import com.springcloud.service.auth.service.base.IUserService;
import com.springcloud.service.auth.util.AuthParamUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>
 * <li>  find user from {@link User}
 * <li> support {@link User#username},{@link User#mobilePhoneNumber}
 * <li> use {@link UserConverter} to convert {@link User} to {@link UserDetails}
 *
 * @author liubo
 */
public class MyUserDetailsService implements UserDetailsService {
    private IUserService userService;
    private UserConverter userConverter;

    public MyUserDetailsService(IUserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    /**
     * @param username format of username#usertype  (username = username or phone)
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userType = AuthParamUtils.getUserType();
        User user = userService.findUserByIdentity(username, userType, false);

        if (user == null) {
            throw new UsernameNotFoundException(AuthError.USER_NOT_EXISTS.getMsg());
        }

        return userConverter.convert(user);
    }

}
