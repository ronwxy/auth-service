package com.springcloud.api.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Setter
@Getter
@Accessors(chain = true)
public class VerifyCodeDto implements Serializable {
    /**
     * the same as the {@link ClientDetailsDto#clientId}
     */
    private String appId;
    private String phone;
    private String code;
}
