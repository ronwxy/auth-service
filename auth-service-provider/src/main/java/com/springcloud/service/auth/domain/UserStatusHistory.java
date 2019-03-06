package com.springcloud.service.auth.domain;

import com.springboot.autoconfig.tkmapper.domain.FixedIdBaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "sys_user_status_history")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class UserStatusHistory extends FixedIdBaseDomain<Long> {

    private static final long serialVersionUID = 1L;

    private Date opDate;
    private String reason;
    private String status;
    private Long opUserId;
    private Long userId;

}
