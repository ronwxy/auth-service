package com.springcloud.service.auth.service.biz.vo;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

/**
 * @author liubo
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"apiResource"})
@ToString
public class ApiResourcePermission implements Serializable {
    private ApiResource apiResource;
    private Set<String> roles;
}
