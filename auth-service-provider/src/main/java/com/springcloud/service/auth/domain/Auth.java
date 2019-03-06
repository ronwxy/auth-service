package com.springcloud.service.auth.domain;

import com.springboot.autoconfig.tkmapper.domain.FixedIdBaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * <p>
 * <p>
 * </p>
 */
@Table(name = "sys_auth")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class Auth extends FixedIdBaseDomain<Long> {

    private static final long serialVersionUID = 1L;

    private Long groupId;
    private Long jobId;
    private Long organizationId;
    private String roleIds;
    private String type;
    private Long userId;
}
