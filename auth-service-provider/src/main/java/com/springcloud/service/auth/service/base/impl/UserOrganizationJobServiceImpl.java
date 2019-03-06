package com.springcloud.service.auth.service.base.impl;

import com.springboot.autoconfig.tkmapper.service.BaseService;
import com.springcloud.service.auth.domain.UserOrganizationJob;
import com.springcloud.service.auth.service.base.IUserOrganizationJobService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liubo
 * @since 2018-04-03
 */
@Service
public class UserOrganizationJobServiceImpl extends BaseService<Long, UserOrganizationJob> implements IUserOrganizationJobService {


    @Override
    public Long findOrganizationByUserId(Long userId) {
        List<UserOrganizationJob> tmp = findByUserId(userId);
        if (CollectionUtils.isNotEmpty(tmp)) {
            return tmp.stream().findFirst().get().getOrganizationId();
        }
        return null;
    }

    @Override
    public List<UserOrganizationJob> findByUserId(Long userId) {
        if (userId == null) {
            return Collections.emptyList();
        }
        Example example = new Example(UserOrganizationJob.class);
        example.createCriteria().andEqualTo("userId", userId);
        return selectByExample(example);
    }

    @Override
    public Set<Long> findJobIdsByUserId(Long userId) {
        List<UserOrganizationJob> tmp = findByUserId(userId);
        return tmp.stream().mapToLong(UserOrganizationJob::getJobId).boxed().collect(Collectors.toSet());
    }

    public List<UserOrganizationJob> findByJobId(Long jobId) {
        if (jobId == null) {
            return Collections.emptyList();
        }
        Example example = new Example(UserOrganizationJob.class);
        example.createCriteria().andEqualTo("jobId", jobId);
        return selectByExample(example);
    }

    public Collection<Long> findUserIdsByJobId(Long jobId){
        List<UserOrganizationJob> tmp = findByJobId(jobId);
        return tmp.stream().mapToLong(UserOrganizationJob::getUserId).boxed().collect(Collectors.toList());
    }

    public List<UserOrganizationJob> findByJobIds(Collection<Long> jobIds) {
        if (CollectionUtils.isEmpty(jobIds)) {
            return Collections.emptyList();
        }
        Example example = new Example(UserOrganizationJob.class);
        example.createCriteria().andIn("jobId", jobIds);
        return selectByExample(example);
    }
    public Collection<Long> findUserIdsByJobIds(Collection<Long> jobIds){
        List<UserOrganizationJob> tmp = findByJobIds(jobIds);
        return tmp.stream().mapToLong(UserOrganizationJob::getUserId).boxed().collect(Collectors.toList());
    }

}
