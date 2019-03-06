package com.springcloud.service.auth.domain;

import com.springboot.autoconfig.tkmapper.domain.FixedIdBaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;

@Table(name = "sys_job")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class Job extends FixedIdBaseDomain<Long> {
    private String icon;
    private String name;
    private Long parentId;
    private String parentIds;
    private Boolean isShow;
    private Integer weight;
    private String category;
}
