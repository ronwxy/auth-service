package com.springcloud.api.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ChangePasswordDto implements Serializable {
    private String newPassword;
}
