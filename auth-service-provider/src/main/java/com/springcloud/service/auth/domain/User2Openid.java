package com.springcloud.service.auth.domain;

import com.springboot.autoconfig.tkmapper.domain.FixedIdBaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;

@Table(name = "sys_user_2_openid")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class User2Openid extends FixedIdBaseDomain<Long> {

    private static final long serialVersionUID = 1L;

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
