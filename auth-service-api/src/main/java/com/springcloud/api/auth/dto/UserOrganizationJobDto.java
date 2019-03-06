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
public class UserOrganizationJobDto implements Serializable {
    private Long id;
    private Long jobId;
    private Long organizationId;
    private Long userId;
}
