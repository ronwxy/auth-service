package com.springcloud.service.auth.service.base;

import com.springboot.autoconfig.tkmapper.service.IService;
import com.springcloud.service.auth.domain.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liubo
 * @since 2018-04-03
 */
public interface IUserService extends IService<Long, User> {

    User findUserByUsername(String username, String userType, Boolean deleted);

    User findUserByPhone(String phone, String userType, Boolean deleted);

    /**
     * 通过手机号码或用户名获取用户信息
     *
     * @param identity 手机号码或用户名
     * @param deleted
     * @return
     */
    User findUserByIdentity(String identity, String userType, Boolean deleted);

    User findUserByEmail(String email, String type, Boolean deleted);

    User createUserByPhone(String phone, String type, String channel);

    User createUserByPhone(String phone, String type, String channel, String appId, String smsCode, String password);

    User createUserByPhone(String phone, String type, String password, String channel);
    /**
     *  when user is login
     * @param user
     * @param password
     */
    User changePassword(User user, String password);

    /**
     * when user is not login;
     * @param phone
     * @param type
     * @param oldPassword
     * @param newPassword
     */
    User changePassword(String phone, String type, String oldPassword, String newPassword);

    /**
     * when user is not login;
     * @param phone
     * @param type
     * @param newPassword
     */
    User resetPassword(String phone, String type, String newPassword);
}
