package com.springcloud.service.auth.service.base.impl;

import com.springboot.autoconfig.tkmapper.service.BaseService;
import com.springcloud.service.auth.domain.Job;
import com.springcloud.service.auth.service.base.IJobService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl extends BaseService<Long, Job> implements IJobService {
    @Override
    public List<Job> findJobsByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return selectByPks(ids);
    }

    @Override
    public Set<String> findStringJobsByIds(Collection<Long> ids) {
        List<Job> jobs = findJobsByIds(ids);
        return jobs.stream().map(Job::getName).collect(Collectors.toSet());
    }

    @Override
    public List<Job> findJobsByNames(Collection<String> names) {
        if (CollectionUtils.isEmpty(names)) {
            return Collections.emptyList();
        }
        Example example = new Example(Job.class);
        example.createCriteria().andIn("name", names);
        return selectByExample(example);
    }

    @Override
    public Set<Long> findJobIdsByNames(Collection<String> names) {
        List<Job> jobs = findJobsByNames(names);
        return jobs.stream().map(Job::getId).collect(Collectors.toSet());
    }

}
