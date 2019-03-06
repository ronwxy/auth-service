package com.springcloud.service.auth.domain;

import com.google.common.base.Strings;
import com.springboot.autoconfig.error.exception.ExceptionUtil;
import com.springboot.autoconfig.tkmapper.domain.FixedIdBaseDomain;
import com.springcloud.service.auth.error.AuthError;
import com.springcloud.service.auth.util.RegexUtils;
import com.springcloud.service.auth.util.security.Md5Utils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "sys_user")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class User extends FixedIdBaseDomain<Long> {

    public static final String DEFAULT_PASSWORD = "123456";
    public static final String DEFAULT_USERNAME_PREFIX = "UNAME_";
    private static final long serialVersionUID = 1L;
//    private Boolean admin;
    private Date createTime;
    private Date updateTime;
    private Boolean deleted;
    private String email;
    private String phone;
    private String password;
    private String salt;
    private String status;
    private String username;
    private String channel;
    private String type;

    public static String generatePassword(String username, String password, String salt) {
        return Md5Utils.hash(username + password + salt);
    }

    private String generateTypeFragment(String type) {
        return type + "_";
    }

    private void generateSalt() {
        setSalt(RandomStringUtils.randomAlphanumeric(10));
    }

    public String generatePassword() {
        if (Strings.isNullOrEmpty(username)) {
            ExceptionUtil.rethrowClientSideException(AuthError.INVALID_USERNAME);
        }

        if (Strings.isNullOrEmpty(password)) {
            password = DEFAULT_PASSWORD;
        }
        generateSalt();
        String encryptedPassword = generatePassword(username, password, salt);
        setPassword(encryptedPassword);
        return encryptedPassword;
    }

    public String generateDefaultUserNameByPhone() {
        if (Strings.isNullOrEmpty(phone) || !RegexUtils.isChinaMobilePhone(phone)) {
            ExceptionUtil.rethrowClientSideException(AuthError.INVALID_PHONE);
        }
        if (Strings.isNullOrEmpty(type)) {
            ExceptionUtil.rethrowClientSideException(AuthError.INVALID_USER_TYPE);
        }
        return DEFAULT_USERNAME_PREFIX + generateTypeFragment(type) + phone;
    }

}
