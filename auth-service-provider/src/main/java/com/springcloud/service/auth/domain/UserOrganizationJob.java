package com.springcloud.service.auth.domain;

import com.springboot.autoconfig.tkmapper.domain.FixedIdBaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;

@Table(name = "sys_user_organization_job")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class UserOrganizationJob extends FixedIdBaseDomain<Long> {

    private static final long serialVersionUID = 1L;

    private Long jobId;
    private Long organizationId;
    private Long userId;

}
