package com.springcloud.service.auth.security.spi.openid;

import com.springcloud.service.auth.domain.User;
import com.springcloud.service.auth.domain.User2Openid;
import com.springcloud.service.auth.error.AuthError;
import com.springcloud.service.auth.security.spi.support.UserConverter;
import com.springcloud.service.auth.service.base.IUser2OpenidService;
import com.springcloud.service.auth.service.base.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

/**
 * <p>
 * <li> find {@link User#id} from {@link User2Openid#getUserId()}
 * <li> find user from {@link User}
 * <li> support {@link User2Openid#openid}
 * <li> use {@link UserConverter} to convert {@link User} to {@link UserDetails}
 *
 * @author liubo
 */
public class OpenIdUserDetailsService implements UserDetailsService {

    private IUserService userService;

    private IUser2OpenidService user2OpenidService;

    private UserConverter userConverter;

    public OpenIdUserDetailsService(IUserService userService, IUser2OpenidService user2OpenidService
            , UserConverter userConverter) {
        this.userService = userService;
        this.user2OpenidService = user2OpenidService;
        this.userConverter = userConverter;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User2Openid user2Openid = user2OpenidService.selectOne(new User2Openid().setOpenid(username));
        if (Objects.isNull(user2Openid)) {
            throw new UsernameNotFoundException(AuthError.USER_NOT_EXISTS.getMsg());
        }
        User user = userService.selectByPk(user2Openid.getUserId());
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(AuthError.USER_NOT_EXISTS.getMsg());
        }
        return userConverter.convert(user);
    }
}
