package com.springcloud.api.auth.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Accessors(chain = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class UserDto implements Serializable {
    private Long id;
    private Date createDate;
    private String email;
    private String mobilePhoneNumber;
    private String status;
    private String username;
    private String channel;
    private String type;
}
