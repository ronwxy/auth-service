package com.springcloud.service.auth.service.base;

import com.springboot.autoconfig.tkmapper.service.IService;
import com.springcloud.service.auth.domain.UserOrganizationJob;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liubo
 * @since 2018-04-03
 */
public interface IUserOrganizationJobService extends IService<Long, UserOrganizationJob> {
    /**
     * user can only belong to one organization;
     *
     * @param userId
     * @return
     */
    Long findOrganizationByUserId(Long userId);

    List<UserOrganizationJob> findByUserId(Long userId);

    Set<Long> findJobIdsByUserId(Long userId);

    List<UserOrganizationJob> findByJobId(Long jobId);

    Collection<Long> findUserIdsByJobId(Long jobId);

    List<UserOrganizationJob> findByJobIds(Collection<Long> jobIds);

    Collection<Long> findUserIdsByJobIds(Collection<Long> jobIds);

}
