package com.springcloud.service.auth.service.base.impl;

import com.google.common.base.Strings;
import com.springboot.autoconfig.error.exception.ExceptionUtil;
import com.springboot.autoconfig.tkmapper.service.BaseService;
import com.springcloud.service.auth.domain.User;
import com.springcloud.service.auth.error.AuthError;
import com.springcloud.service.auth.security.sms.ISMSCodeService;
import com.springcloud.service.auth.service.base.IUserService;
import com.springcloud.service.auth.util.UserStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liubo
 * @since 2018-04-03
 */
@Service
public class UserServiceImpl extends BaseService<Long, User> implements IUserService {

    private ISMSCodeService smsService;

    @Override
    public User findUserByUsername(String username, String userType, Boolean deleted) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        Example example = new Example(User.class);
        example.createCriteria()
                .andEqualTo("username", username)
                .andEqualTo("type", userType)
                .andEqualTo("deleted", deleted);
        return selectOneByExample(example);
    }

    @Override
    public User findUserByPhone(String phone, String userType, Boolean deleted) {
        if (StringUtils.isBlank(phone)) {
            return null;
        }
        Example example = new Example(User.class);
        example.createCriteria()
                .andEqualTo("mobilePhoneNumber", phone)
                .andEqualTo("type", userType)
                .andEqualTo("deleted", deleted);
        return selectOneByExample(example);
    }

    @Override
    public User findUserByIdentity(String identity, String userType, Boolean deleted) {
        User user;
        if (NumberUtils.isDigits(identity)) {
            user = findUserByPhone(identity, userType, deleted);
        } else {
            user = findUserByUsername(identity, userType, deleted);
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email, String type, Boolean deleted) {
        if (StringUtils.isBlank(email)) {
            return null;
        }
        Example example = new Example(User.class);
        example.createCriteria()
                .andEqualTo("email", email)
                .andEqualTo("type", type)
                .andEqualTo("deleted", deleted);

        return selectOneByExample(example);
    }

    @Transactional
    @Override
    public User createUserByPhone(String phone, String type, String channel) {
        return createUserByPhone(phone, type, null, channel);
    }

    @Autowired
    public void setSmsService(ISMSCodeService smsService) {
        this.smsService = smsService;
    }

    @Override
    public User createUserByPhone(String phone, String type, String channel, String appId, String smsCode, String password) {
        smsService.verifySMSCode(appId, phone, smsCode);
        return createUserByPhone(phone, type, password, channel);
    }

    @Transactional
    @Override
    public User createUserByPhone(String phone, String type, String password, String channel) {
        User user = findUserByPhone(phone, type, false);
        if (user == null) {
            user = new User();
            user.setPhone(phone);
            user.setType(type);
            user.setUsername(user.generateDefaultUserNameByPhone());
            user.setPassword(password);
            user.setPassword(user.generatePassword());
            user.setStatus(UserStatusEnum.normal.name());
            user.setCreateTime(new Date());
            user.setDeleted(false);
            user.setChannel(channel);
            user = create(user);
        } else {
            changePassword(user, password);
        }
        return user;
    }

    @Override
    public User changePassword(User user, String password) {
        String oldEncryptPassword = user.getPassword();
        String newEncryptPassword = User.generatePassword(user.getUsername(), password, user.getSalt());
        if (Objects.equals(oldEncryptPassword, newEncryptPassword)) {
            return user;
        }
        user.setPassword(newEncryptPassword);
        return updateSelective(user);
    }

    @Override
    public User changePassword(String identity, String type, String oldPassword, String newPassword) {
        //validate
        if (Strings.isNullOrEmpty(oldPassword) || Strings.isNullOrEmpty(newPassword)) {
            ExceptionUtil.rethrowClientSideException(AuthError.INVALID_PASSWORD);
        }

        User user = findUserByIdentity(identity, type, false);
        if (user == null) {
            ExceptionUtil.rethrowClientSideException(AuthError.USER_NOT_EXISTS);
        }
        String storedPassword = user.getPassword();
        String encryptOldPassword = User.generatePassword(user.getUsername(), oldPassword, user.getSalt());
        if (!Objects.equals(storedPassword, encryptOldPassword)) {
            ExceptionUtil.rethrowClientSideException(AuthError.INVALID_PASSWORD);
        }
        return changePassword(user, newPassword);
    }

    @Override
    public User resetPassword(String phone, String type, String newPassword) {
        if (StringUtils.isEmpty(newPassword)) {
            ExceptionUtil.rethrowClientSideException(AuthError.INVALID_PASSWORD);
        }
        User user = findUserByIdentity(phone, type, false);
        return changePassword(user, newPassword);


    }
}
