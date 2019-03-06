package com.springcloud.service.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.autoconfig.tkmapper.domain.FixedIdBaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.beans.Transient;

/**
 * <p>
 * <p>
 * </p>
 *
 */
@Table(name = "sys_group_relation")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class GroupRelation extends FixedIdBaseDomain<Long> {

    private static final long serialVersionUID = 1L;

    private Long endUserId;
    private Long groupId;
    private Long organizationId;
    private Long startUserId;
    private Long userId;

    @Transient
    @JsonIgnore
    public boolean isDirectUser() {
        return userId != null;
    }

    @Transient
    @JsonIgnore
    public boolean isIntervalUser() {
        return startUserId != null && endUserId != null;
    }

}
