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
@Table(name = "sys_organization")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class Organization extends FixedIdBaseDomain<Long> {

    private static final long serialVersionUID = 1L;

    private String icon;
    private String name;
    private Long parentId;
    private String parentIds;
    private Boolean isShow;
    private String type;
    private Integer weight;
    private Integer siteId;
}
