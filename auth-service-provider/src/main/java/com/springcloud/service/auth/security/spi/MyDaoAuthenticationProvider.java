package com.springcloud.service.auth.security.spi;

import com.springcloud.service.auth.error.AuthError;
import com.springcloud.service.auth.util.AuthParamUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

public class MyDaoAuthenticationProvider extends DaoAuthenticationProvider implements SmartInitializingSingleton {

    private CompositeUserDetailsChecker preChecker;
    private CompositeUserDetailsChecker afterPasswordInvalidChecker;

    public void setPreChecker(CompositeUserDetailsChecker preChecker) {
        this.preChecker = preChecker;
    }

    public void setAfterPasswordInvalidChecker(CompositeUserDetailsChecker afterPasswordInvalidChecker) {
        this.afterPasswordInvalidChecker = afterPasswordInvalidChecker;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(AuthError.BAD_CREDENTIALS.getMsg());
        }
        String presentedPassword = authentication.getCredentials().toString();

        if (!getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
            logger.debug("Authentication failed: password does not match stored value");
            afterPasswordInvalidChecker.check(userDetails);
            throw new BadCredentialsException(AuthError.BAD_CREDENTIALS.getMsg());
        }
    }


    @Override
    public void afterSingletonsInstantiated() {
        UserDetailsChecker defaultPreChecker = getPreAuthenticationChecks();
        preChecker.getCheckers().add(0, defaultPreChecker);
        setPreAuthenticationChecks(preChecker);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        //only support UsernamePasswordAuthenticationToken and smsCode is null;
        return super.supports(authentication) && AuthParamUtils.hasPassword();
    }
}
