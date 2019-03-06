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
 *
 */
@Table(name = "sys_group")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class Group extends FixedIdBaseDomain<Long> {

    private static final long serialVersionUID = 1L;

    private Boolean defaultGroup;
    private String name;
    private Boolean isShow;
    private String type;

}
