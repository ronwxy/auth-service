package com.springcloud.service.auth.security;

import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * mark the {@link UserDetailsChecker} to use pre authentication;
 * @author liubo
 */
public interface PreAuthenticationChecker extends UserDetailsChecker {
}
