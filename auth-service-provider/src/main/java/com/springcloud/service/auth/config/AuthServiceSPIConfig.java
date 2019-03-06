package com.springcloud.service.auth.config;

import com.springcloud.service.auth.security.AfterPasswordFailedChecker;
import com.springcloud.service.auth.security.PreAuthenticationChecker;
import com.springcloud.service.auth.security.lock.ILockManager;
import com.springcloud.service.auth.security.retry.IRetryLimitedManager;
import com.springcloud.service.auth.security.spi.CompositeUserDetailsChecker;
import com.springcloud.service.auth.security.spi.MyDaoAuthenticationProvider;
import com.springcloud.service.auth.security.spi.MyUserDetailsService;
import com.springcloud.service.auth.security.spi.RetryLimitAfterPasswordFailedChecker;
import com.springcloud.service.auth.security.spi.support.UserConverter;
import com.springcloud.service.auth.service.base.IAuthService;
import com.springcloud.service.auth.service.base.IRoleService;
import com.springcloud.service.auth.service.base.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 */
@Configuration
public class AuthServiceSPIConfig {


    private List<UserDetailsChecker> preAuthenticationCheckers;
    private List<UserDetailsChecker> afterPasswordFailedCheckers;

    @Autowired(required = false)
    public void setPreAuthenticationCheckers(List<PreAuthenticationChecker> preAuthenticationCheckers) {
        this.preAuthenticationCheckers = new ArrayList<>();
        this.preAuthenticationCheckers.addAll(
                Optional.ofNullable(preAuthenticationCheckers).orElse(Collections.emptyList())
        );
    }

    @Autowired(required = false)
    public void setAfterPasswordFailedCheckers(List<AfterPasswordFailedChecker> afterPasswordFailedCheckers) {
        this.afterPasswordFailedCheckers = new ArrayList<>();
        this.afterPasswordFailedCheckers.addAll(Optional.ofNullable(afterPasswordFailedCheckers).
                orElse(Collections.emptyList()));

    }

    @Bean
    public UserDetailsService userDetailsService(IUserService userService,
                                                 UserConverter userConverter) {
        MyUserDetailsService myUserDetailsService = new MyUserDetailsService(
                userService, userConverter
        );
        return myUserDetailsService;
    }

    @Bean
    public UserConverter userConverter(IAuthService authService, IRoleService roleService,
                                       ILockManager lockManager) {
        return new UserConverter(authService, roleService, lockManager);
    }


    /**
     * default authenticationProvider
     *
     * @return
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService
    ) {
        MyDaoAuthenticationProvider daoAuthenticationProvider = new MyDaoAuthenticationProvider();
        daoAuthenticationProvider.setPreChecker(
                new CompositeUserDetailsChecker(preAuthenticationCheckers)
        );
        daoAuthenticationProvider.setAfterPasswordInvalidChecker(new CompositeUserDetailsChecker(afterPasswordFailedCheckers));
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public RetryLimitAfterPasswordFailedChecker retryLimitChecker(IRetryLimitedManager manager) {
        return new RetryLimitAfterPasswordFailedChecker(manager);
    }


}
