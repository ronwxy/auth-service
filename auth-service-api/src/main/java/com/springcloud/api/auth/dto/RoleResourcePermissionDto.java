package com.springcloud.api.auth.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class RoleResourcePermissionDto implements Serializable {
    private Long id;
    private String permissionIds;
    private Long resourceId;
    private Long roleId;
}
