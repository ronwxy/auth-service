package com.springcloud.service.auth.security.spi;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompositeUserDetailsChecker implements UserDetailsChecker {
    private List<UserDetailsChecker> checkers;

    public CompositeUserDetailsChecker(List<UserDetailsChecker> checkers) {
        this.checkers = checkers;
    }

    public List<UserDetailsChecker> getCheckers() {
        return Optional.ofNullable(checkers).orElse(new ArrayList<>());
    }

    @Override
    public void check(UserDetails toCheck) {
        if (CollectionUtils.isEmpty(checkers)) {
            return;
        }
        for (UserDetailsChecker checker : checkers) {
            checker.check(toCheck);
        }
    }
}
