package com.springcloud.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterByPhoneDto implements Serializable {
    private String phone;
    private String type;
    private String appId;
    private String smsCode;
    private String password;
    private String channel;
}
