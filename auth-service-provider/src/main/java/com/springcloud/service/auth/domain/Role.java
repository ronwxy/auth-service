package com.springcloud.service.auth.domain;

import com.springboot.autoconfig.tkmapper.domain.FixedIdBaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;

@Table(name = "sys_role")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class Role extends FixedIdBaseDomain<Long> {

    private static final long serialVersionUID = 1L;

    private String description;
    private String name;
    private String role;
    private Boolean isShow;

}
