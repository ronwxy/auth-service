package com.springcloud.service.auth.security;

import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * mark {@link UserDetailsChecker} to use after check the password;
 *
 * @author liubo
 */
public interface AfterPasswordFailedChecker extends UserDetailsChecker {
}
