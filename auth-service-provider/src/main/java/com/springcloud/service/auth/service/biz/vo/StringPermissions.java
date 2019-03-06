package com.springcloud.service.auth.service.biz.vo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = "userId")
public class StringPermissions implements Serializable {
    private Long userId;
    private Set<String> jobs = new HashSet<>();
    private Set<String> roles = new HashSet<>();
    private Set<StringResourcePermission> resourcePermissions;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @EqualsAndHashCode(of = "resource")
    public static class StringResourcePermission {
        private String resource;
        private Set<String> permissions;
    }
}
