package com.springcloud.api.auth.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * @author liubo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"apiResource"})
@ToString
public class ApiResourcePermissionDto implements Serializable {
    private ApiResourceDto apiResource;
    private Set<String> roles;
}
