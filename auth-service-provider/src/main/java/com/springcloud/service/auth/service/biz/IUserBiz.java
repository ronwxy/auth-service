package com.springcloud.service.auth.service.biz;

import com.springcloud.service.auth.domain.Group;
import com.springcloud.service.auth.domain.Job;
import com.springcloud.service.auth.domain.Role;
import com.springcloud.service.auth.domain.User;

import java.util.Collection;

/**
 * user business controller
 *
 * @author liubo
 */
public interface IUserBiz {

    Collection<User> queryUsersByJobIds(Collection<Long> jobIds);

    Collection<User> queryUsersByJobNames(Collection<String> jobNames);


    Long createOrUpdateUserWithAuthorities(User user, Collection<Job> jobs, Collection<Role> roles, Collection<Group> groups
            , boolean jobsEmpty2remove, boolean rolesEmpty2remove, boolean groupsEmpty2remove, Long createUserId);

    Collection<Job> findRealJobs(Collection<Long> jobIds, Collection<String> jobNames);

    Collection<Role> findRealRoles(Collection<Long> roleIds, Collection<String> roleNames);

    Collection<Group> findRealGroups(Collection<Long> groupIds, Collection<String> groupNames);
}
