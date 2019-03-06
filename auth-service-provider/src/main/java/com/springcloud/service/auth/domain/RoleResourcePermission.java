package com.springcloud.service.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.autoconfig.tkmapper.domain.FixedIdBaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Table(name = "sys_role_resource_permission")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class RoleResourcePermission extends FixedIdBaseDomain<Long> {

    private static final long serialVersionUID = 1L;

    private String permissionIds;
    private Long resourceId;
    private Long roleId;

    @Transient
    @JsonIgnore
    public Set<Long> getPermissionIdSet() {
        if (StringUtils.isEmpty(getPermissionIds())) {
            return Collections.emptySet();
        }
        return Arrays.stream(StringUtils.split(getPermissionIds(), Resource.SEPARATE_CHAR))
                .map(Long::valueOf).
                        collect(Collectors.toSet());
    }

}
