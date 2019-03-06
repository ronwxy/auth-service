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
@EqualsAndHashCode(of = {"id"})
public class RoleDto implements Serializable {
    private Long id;
    private String description;
    private String name;
    private String role;
    private Boolean isShow;
}
