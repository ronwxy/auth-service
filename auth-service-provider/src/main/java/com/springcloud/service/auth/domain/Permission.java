package com.springcloud.service.auth.domain;

import com.springboot.autoconfig.tkmapper.domain.FixedIdBaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;

@Table(name = "sys_permission")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class Permission extends FixedIdBaseDomain<Long> {

    private static final long serialVersionUID = 1L;

    private String description;
    private String name;
    private String permission;
    private Boolean isShow;

}
