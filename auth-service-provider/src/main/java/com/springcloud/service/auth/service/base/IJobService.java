package com.springcloud.service.auth.service.base;

import com.springboot.autoconfig.tkmapper.service.IService;
import com.springcloud.service.auth.domain.Job;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IJobService extends IService<Long, Job> {
    List<Job> findJobsByIds(Collection<Long> ids);

    Set<String> findStringJobsByIds(Collection<Long> ids);

    List<Job> findJobsByNames(Collection<String> names);

    Set<Long> findJobIdsByNames(Collection<String> names);
}
