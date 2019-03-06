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
public class User2OpenidDto implements Serializable {
    private Long id;
    /**
     * 用户ID，指向sys_user.id
     */
    private Long userId;
    /**
     * 第三方系统名称(weixin,qq,weibo)
     */
    private String openidSource;
    /**
     * 第三方系统返回的openid
     */
    private String openid;
    /**
     * 第三方系统返回的token_id
     */
    private String token;
}
