package com.springcloud.service.auth.mapper;

import com.springboot.autoconfig.tkmapper.mapper.BaseMapper;
import com.springcloud.service.auth.domain.Job;
import org.springframework.stereotype.Repository;

@Repository
public interface JobMapper extends BaseMapper<Job> {
}
