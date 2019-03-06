package com.springcloud.api.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResetPasswordDto implements Serializable {
    private String phone;
    private String type;
    private String newPassword;
}
