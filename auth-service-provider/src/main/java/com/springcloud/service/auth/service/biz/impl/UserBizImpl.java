package com.springcloud.service.auth.service.biz.impl;

import com.springboot.autoconfig.error.exception.ExceptionUtil;
import com.springcloud.service.auth.domain.*;
import com.springcloud.service.auth.error.AuthError;
import com.springcloud.service.auth.mapper.AuthMapper;
import com.springcloud.service.auth.mapper.GroupRelationMapper;
import com.springcloud.service.auth.mapper.UserOrganizationJobMapper;
import com.springcloud.service.auth.service.base.*;
import com.springcloud.service.auth.service.biz.IUserBiz;
import com.springcloud.service.auth.util.AuthorizationType;
import com.springcloud.service.auth.util.security.Md5Utils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * user biz impl
 *
 * @author liubo
 */
@Service
public class UserBizImpl implements IUserBiz {
    private IUserService userService;
    private IAuthService authService;
    private AuthMapper authMapper;
    private IUserOrganizationJobService userOrganizationJobService;
    private UserOrganizationJobMapper userOrganizationJobMapper;
    private GroupRelationMapper groupRelationMapper;
    private IGroupRelationService groupRelationService;
    private IRoleService roleService;
    private IGroupService groupService;
    private IJobService jobService;

    @Autowired
    public void setAuthMapper(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @Autowired
    public void setJobService(IJobService jobService) {
        this.jobService = jobService;
    }

    @Autowired
    public void setGroupService(IGroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setGroupRelationService(IGroupRelationService groupRelationService) {
        this.groupRelationService = groupRelationService;
    }

    @Override
    public Collection<User> queryUsersByJobIds(Collection<Long> jobIds) {
        Collection<Long> userIds = userOrganizationJobService.findByJobIds(jobIds).stream().mapToLong(UserOrganizationJob::getUserId).boxed().collect(Collectors.toList());
        return userService.selectByPks(userIds);
    }

    @Override
    public Collection<User> queryUsersByJobNames(Collection<String> jobNames) {
        Collection<Long> jobIds = jobService.findJobIdsByNames(jobNames);
        Collection<Long> userIds = userOrganizationJobService.findByJobIds(jobIds).stream().mapToLong(UserOrganizationJob::getUserId).boxed().collect(Collectors.toList());
        return userService.selectByPks(userIds);
    }

    @Autowired
    public void setUserOrganizationJobMapper(UserOrganizationJobMapper userOrganizationJobMapper) {
        this.userOrganizationJobMapper = userOrganizationJobMapper;
    }

    @Autowired
    public void setGroupRelationMapper(GroupRelationMapper groupRelationMapper) {
        this.groupRelationMapper = groupRelationMapper;
    }

    @Autowired
    public void setUserOrganizationJobService(IUserOrganizationJobService userOrganizationJobService) {
        this.userOrganizationJobService = userOrganizationJobService;
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAuthService(IAuthService authService) {
        this.authService = authService;
    }


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Long createOrUpdateUserWithAuthorities(User user, Collection<Job> jobs, Collection<Role> roles, Collection<Group> groups
            , boolean jobsEmpty2remove, boolean rolesEmpty2remove, boolean groupsEmpty2remove, Long createUserId) {

        if (Objects.isNull(user)) {
            return null;
        }
        boolean isCreate = Objects.isNull(user.getId());
        User userExist = findExistUser(user.getId(), user.getUsername(), user.getEmail(), user.getPhone(), user.getType(), isCreate);
        if (isCreate) {
            String salt = RandomStringUtils.randomAlphanumeric(10);
            String toEncryptPassword = user.getUsername() + user.getPassword()
                    + salt;
            user.setSalt(salt)
                    .setPassword(Md5Utils.hash(toEncryptPassword))
                    .setCreateTime(new Date())
                    .setDeleted(false);
            userExist = userService.create(user);
        } else {
            //to check password;
            if (user.getStatus() != null) {
                userExist.setStatus(user.getStatus());
            }
            if (user.getUsername() != null) {
                userExist.setUsername(user.getUsername());
            }
            if (user.getEmail() != null) {
                userExist.setEmail(user.getEmail());
            }
            if (user.getPhone() != null) {
                userExist.setPhone(user.getPhone());
            }
            if (StringUtils.isNotEmpty(user.getPassword())) {
                //TODO
            }
            userService.updateSelective(userExist);
        }
        User finalUserExist = userExist;
        //find organization by operator user;
        Long orgId = userOrganizationJobService.findOrganizationByUserId(createUserId);
        authorizeJobs2User(jobs, finalUserExist, orgId, jobsEmpty2remove);
        //find auth by user
        authorizeRoles2User(roles, finalUserExist, rolesEmpty2remove);
        authorizeGroups2User(groups, finalUserExist, orgId, groupsEmpty2remove);
        return userExist.getId();
    }

    @Override
    public Collection<Job> findRealJobs(Collection<Long> jobIds, Collection<String> jobNames) {
        List<Job> jobs = jobService.findJobsByIds(jobIds);
        Collection<Job> result = new ArrayList<>(jobs);
        result.addAll(jobService.findJobsByNames(jobNames));
        return result;
    }

    @Override
    public Collection<Role> findRealRoles(Collection<Long> roleIds, Collection<String> roleNames) {
        List<Role> roles = roleService.findRolesByIds(roleIds);
        Collection<Role> result = new ArrayList<>(roles);
        result.addAll(roleService.findRolesByStringRoles(roleNames));
        return result;
    }

    @Override
    public Collection<Group> findRealGroups(Collection<Long> groupIds, Collection<String> groupNames) {
        List<Group> groups = groupService.findGroupsByIds(groupIds);
        Collection<Group> result = new ArrayList<>(groups);
        result.addAll(groupService.findGroupsByNames(groupNames));
        return result;
    }

    private void authorizeGroups2User(Collection<Group> groups, User finalUserExist, Long orgId, boolean groupsEmpty2remove) {
        if (CollectionUtils.isEmpty(groups) && !groupsEmpty2remove) {
            return;
        }
        List<GroupRelation> groupRelationsExist = groupRelationService.findByUserId(finalUserExist.getId());
        Set<Long> existGroupIds = groupRelationsExist.stream().mapToLong(GroupRelation::
                getGroupId).boxed().collect(Collectors.toSet());
        Set<Group> group2Insert = groups.stream().filter(g -> !existGroupIds.contains(g.getId()))
                .collect(Collectors.toSet());
        Collection<Group> finalGroups = groups;
        Set<GroupRelation> group2remove = groupRelationsExist.stream().filter(gre -> finalGroups.
                stream().mapToLong(Group::getId).boxed().noneMatch(g -> g.equals(gre.getGroupId())))
                .collect(Collectors.toSet());


        List<GroupRelation> groupRelationsToInsert = new ArrayList<>(group2Insert.size());
        group2Insert.forEach(group -> {
            GroupRelation newGroupRelation = new GroupRelation();
            newGroupRelation.setGroupId(group.getId())
                    .setUserId(finalUserExist.getId())
                    .setOrganizationId(orgId);
            groupRelationsToInsert.add(newGroupRelation);
        });
        if (CollectionUtils.isNotEmpty(groupRelationsToInsert)) {
            groupRelationMapper.insertList(groupRelationsToInsert);
        }
        groupRelationService.removeGroupRelations(group2remove);
    }

    private void authorizeRoles2User(Collection<Role> roles, User finalUserExist, boolean rolesEmpty2remove) {
        if (CollectionUtils.isEmpty(roles) && !rolesEmpty2remove) {
            return;
        }
        Auth userAuthExist = authService.findAuthAuthorized2User(finalUserExist.getId());
        String roleToSave = roles.stream().map(r -> String.valueOf(r.getId()))
                .collect(Collectors.joining(","));
        if (userAuthExist != null) {//exist
            if (CollectionUtils.isNotEmpty(roles)) {
                userAuthExist.setRoleIds(roleToSave);
                authService.updateSelective(userAuthExist);
            } else {
                authMapper.delete(userAuthExist);
            }
        } else {
            if (CollectionUtils.isNotEmpty(roles)) {
                Auth newAuth = new Auth();
                newAuth.setUserId(finalUserExist.getId())
                        .setOrganizationId(0L)
                        .setGroupId(0L)
                        .setJobId(0L)
                        .setType(AuthorizationType.user.name())
                        .setRoleIds(roleToSave);
                authService.create(newAuth);
            }
        }
    }

    private void authorizeJobs2User(Collection<Job> jobs, User finalUserExist, Long orgId, boolean emptyJobs2Remove) {
        if (CollectionUtils.isEmpty(jobs) && !emptyJobs2Remove) {
            return;
        }
        List<UserOrganizationJob> relationExists = userOrganizationJobService.findByUserId(finalUserExist.
                getId());
        Set<Long> existsJobIds =
                relationExists.stream().
                        filter(r -> r.getOrganizationId().equals(orgId))
                        .mapToLong(UserOrganizationJob::getJobId).boxed().collect(Collectors.toSet());

        Set<Job> jobs2Inserts =
                jobs.stream().filter(j -> !existsJobIds.contains(j.getId()))
                        .collect(Collectors.toSet());
        Collection<Job> finalJobs = jobs;
        Set<UserOrganizationJob> jobs2Remove =
                relationExists.stream().filter(uoj -> finalJobs
                        .stream().mapToLong(Job::getId).boxed().noneMatch(j -> j.equals(uoj.getJobId()))
                        && orgId.equals(uoj.getOrganizationId())).collect(Collectors.toSet());
        List<UserOrganizationJob> toInsert = new ArrayList<>(jobs2Inserts.size());

        jobs2Inserts.forEach(job -> {
            UserOrganizationJob newOrgJob = new UserOrganizationJob();
            newOrgJob.setJobId(job.getId())
                    .setOrganizationId(orgId)
                    .setUserId(finalUserExist.getId());
            toInsert.add(newOrgJob);
        });
        if (CollectionUtils.isNotEmpty(jobs2Inserts)) {
            userOrganizationJobMapper.insertList(toInsert);
        }
        jobs2Remove.forEach(jm -> userOrganizationJobMapper.delete(jm));
    }

    //1.check valid user field by database index;
    //2.all index depends on no null column
    //3.username+type+deleted=false
    //4.email+type+deleted=false
    //5.phone+type+deleted=false
    User findExistUser(Long userId, String username, String email, String phone, String type, boolean throwException) {
        if (userId != null) {
            return userService.selectByPk(userId);
        }
        if (StringUtils.isNotBlank(type)) {
            if (StringUtils.isNotBlank(username)) {
                User existUser = userService.findUserByUsername(username, type, false);
                if (existUser != null) {
                    if (throwException) {
                        ExceptionUtil.rethrowServerSideException(AuthError.EXISTS_USERNAME_TYPE);
                    } else {
                        return existUser;
                    }
                }
            }
            if (StringUtils.isNotBlank(phone)) {
                User existUser = userService.findUserByPhone(phone, type, false);
                if (existUser != null) {
                    if (throwException) {
                        ExceptionUtil.rethrowServerSideException(AuthError.EXISTS_PHONE_TYPE);
                    } else {
                        return existUser;
                    }
                }
            }
            if (StringUtils.isNotBlank(email)) {
                User existUser = userService.findUserByEmail(email, type, false);
                if (existUser != null) {
                    if (throwException) {
                        ExceptionUtil.rethrowServerSideException(AuthError.EXISTS_EMAIL_TYPE);
                    } else {
                        return existUser;
                    }

                }
            }
        }
        return null;
    }
}
