package com.springcloud.service.auth.service.base.impl;

import com.springboot.autoconfig.tkmapper.service.BaseService;
import com.springcloud.service.auth.domain.*;
import com.springcloud.service.auth.service.base.*;
import com.springcloud.service.auth.util.AuthorizationType;
import com.springcloud.service.auth.util.GroupTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liubo
 * @since 2018-04-03
 */
@Service
public class AuthServiceImpl extends BaseService<Long, Auth> implements IAuthService {

    private IUserOrganizationJobService userOrganizationJobService;
    private IGroupService groupService;
    private IGroupRelationService groupRelationService;
    private IRoleService roleService;
    private IJobService jobService;
    private IUserService userService;

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJobService(IJobService jobService) {
        this.jobService = jobService;
    }

    @Autowired
    public void setUserOrganizationJobService(IUserOrganizationJobService userOrganizationJobService) {
        this.userOrganizationJobService = userOrganizationJobService;
    }

    @Autowired
    public void setGroupService(IGroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setGroupRelationService(IGroupRelationService groupRelationService) {
        this.groupRelationService = groupRelationService;
    }

    @Autowired
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Set<Long> findAllRoleIdsByUser(Long userId) {
        Set<Long> result = new HashSet<>();
        result.addAll(findRoleIdsAuthorized2User(userId));
        result.addAll(findRoleIdsAuthorized2Job(userId));
        result.addAll(findRoleIdsAuthorized2Group(userId, null));
        return result;
    }

    @Override
    public List<Role> findAllRolesByUser(Long userId) {
        Set<Long> roleIds = findAllRoleIdsByUser(userId);
        return roleService.findRolesByIds(roleIds);
    }

    @Override
    public Set<Long> findRoleIdsAuthorized2User(Long userId) {
        Example example = new Example(Auth.class);
        example.selectProperties("roleIds").createCriteria().andEqualTo("userId", userId);
        List<Auth> tmp = selectByExample(example);
        return extractRoleIdSet(tmp);
    }

    @Override
    public Set<Long> findRoleIdsAuthorized2Job(Long userId) {
        Example userOrgJobExample = new Example(UserOrganizationJob.class).selectProperties("jobId");
        userOrgJobExample.createCriteria().andEqualTo("userId", userId);
        Set<Long> jobIds = userOrganizationJobService.selectByExample(userOrgJobExample)
                .stream().mapToLong(UserOrganizationJob::getJobId).boxed().collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(jobIds)) {
            return Collections.emptySet();
        }
        Example authExample = new Example(Auth.class).selectProperties("roleIds");
        authExample.createCriteria().andIn("jobId", jobIds);
        List<Auth> tmp = selectByExample(authExample);
        return extractRoleIdSet(tmp);
    }

    @Override
    public Set<Long> extractRoleIdSet(List<Auth> authList) {
        Set<Long> result;
        if (CollectionUtils.isNotEmpty(authList)) {
            result = authList.stream().map(e -> {
                String roleIds = e.getRoleIds();
                if (StringUtils.isNotEmpty(roleIds)) {
                    return roleIds.split(",");
                }
                return new String[0];
            }).flatMap(Stream::of).map(Long::valueOf)
                    .collect(Collectors.toSet());
        } else {
            result = Collections.emptySet();
        }
        return result;
    }

    @Override
    public Set<Long> findRoleIdsAuthorized2Group(Long userId, GroupTypeEnum groupType) {
        boolean findAllGroup = (groupType == null);
        Example groupCondition = new Example(Group.class).selectProperties("id");
        if (!findAllGroup) {
            groupCondition.createCriteria().andEqualTo("type", groupType.name());
        }
        Set<Long> groupIds = groupService.selectByExample(groupCondition)
                .stream().mapToLong(Group::getId)
                .boxed().collect(Collectors.toSet());
        Example groupRelationCondition = new Example(GroupRelation.class).selectProperties("groupId");
        Example.Criteria criteria = groupRelationCondition.createCriteria();
        criteria.andEqualTo("userId", userId);
        if (CollectionUtils.isNotEmpty(groupIds)) {
            criteria.andIn("groupId", groupIds);
        }
        Set<Long> userGroupIds = groupRelationService.selectByExample(groupRelationCondition)
                .stream().mapToLong(GroupRelation::getGroupId).boxed().collect(Collectors.toSet());
        Example authExample = new Example(Auth.class).selectProperties("roleIds");
        if (CollectionUtils.isEmpty(userGroupIds)) {
            return new HashSet<>();
        }
        authExample.createCriteria().andIn("groupId", userGroupIds);
        List<Auth> tmp = selectByExample(authExample);
        return extractRoleIdSet(tmp);
    }

    @Override
    public Auth findAuthAuthorized2User(Long userId) {
        if (userId == null)
            return null;
        Example example = new Example(Auth.class);
        Auth auth = new Auth();
        auth.setType(AuthorizationType.user.name());
        auth.setUserId(userId);
        example.createCriteria().andEqualTo(auth);
        return selectOneByExample(example);
    }


    @Override
    public List<User> findUsersByRoles(Collection<Role> roles, boolean and) {
            if(CollectionUtils.isEmpty(roles)){
                return Collections.emptyList();
            }
        return findUsersByRoleIds(roles.stream().map(Role::getId).collect(Collectors.toList()), and);

    }

    @Override
    public List<User> findUsersByRoleNames(Collection<String> roleNames, boolean and) {
        Collection<Role> roles = roleService.findRolesByStringRoles(roleNames);
        return findUsersByRoles(roles,and);
    }

    @Override
    public List<User> findUsersByRoleIds(Collection<Long> roleIds, boolean and) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        Example authExample = new Example(Auth.class);
        Example.Criteria criteria = authExample.and();
        if (and) {
            roleIds.forEach(roleId -> criteria.andCondition(getRoleIdsColumnCondition(roleId)));
        } else {
            roleIds.forEach(roleId -> criteria.orCondition(getRoleIdsColumnCondition(roleId)));
        }
        List<Auth> authList = selectByExample(authExample);
        // group by auth type;
        Map<String, List<Auth>> typeOfAuth = authList.stream().collect(Collectors.
                groupingBy(Auth::getType));
        // find user
        List<Long> authorized2UserUserIds = Optional.ofNullable(typeOfAuth.get(AuthorizationType.user.name()))
                .orElse(Collections.emptyList()).stream().mapToLong(Auth::getUserId).boxed().
                        collect(Collectors.toList());
        //find jobs
        List<Long> authorized2JobJobIds = Optional.ofNullable(typeOfAuth.get(AuthorizationType.organization_job.name()))
                .orElse(Collections.emptyList()).stream().mapToLong(Auth::getJobId).boxed().collect(Collectors.toList());
        Collection<Long> authorized2JobUserIds = userOrganizationJobService.findUserIdsByJobIds(authorized2JobJobIds);
        //find user group
        List<Long> authorized2GroupGroupIds = Optional.ofNullable(typeOfAuth.get(AuthorizationType.organization_group.name()))
                .orElse(Collections.emptyList()).stream().mapToLong(Auth::getGroupId).boxed().collect(Collectors.toList());
        List<GroupRelation> groupRelations = groupRelationService.findByGroupIds(authorized2GroupGroupIds);
        List<Long> noneNullUserIds = groupRelations.stream().filter(GroupRelation::isDirectUser).mapToLong(GroupRelation::getUserId)
                .boxed().collect(Collectors.toList());
        List<GroupRelation> intervalGroupRelations = groupRelations.stream().filter(GroupRelation::isIntervalUser)
                .collect(Collectors.toList());

        List<Long> directUserIds = new ArrayList<>(authorized2UserUserIds);
        directUserIds.addAll(authorized2JobUserIds);
        directUserIds.addAll(noneNullUserIds);
        Example userExample = new Example(User.class);
        if (CollectionUtils.isNotEmpty(directUserIds)) {
            userExample.and().andIn("id", directUserIds);
        }
        if (CollectionUtils.isNotEmpty(intervalGroupRelations)) {
            Long minUserId = intervalGroupRelations.stream().min((o1, o2) -> (int) (o1.getStartUserId() - o2.getStartUserId()))
                    .get().getStartUserId();
            Long maxUserId = intervalGroupRelations.stream().max((o1, o2) -> (int) (o1.getEndUserId() - o2.getEndUserId()))
                    .get().getEndUserId();
            userExample.or().andGreaterThanOrEqualTo("startUserId", minUserId)
                    .andLessThanOrEqualTo("endUserId", maxUserId);
        }
        return userService.selectByExample(userExample);
    }

    private String getRoleIdsColumnCondition(Long roleId) {
        return roleId + "=any (array_remove(string_to_array(role_ids,',',''),NULL)::int[])";
    }
}
