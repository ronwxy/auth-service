package com.springcloud.service.auth.util;

import com.springboot.common.web.WebParamUtil;
import com.springcloud.api.notify.dto.BasicSMSDto;
import com.springcloud.service.auth.domain.User;
import com.springcloud.service.auth.error.AuthError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Optional;

/**
 * @author liubo
 */
public final class AuthParamUtils {
    //username and password
    public static final String PASSWORD = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;
    public static final String USERNAME = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;
    //appId
    public static final String HEADER_APP_ID = WebParamUtil.HEADER_COOKIE_PREFIX + BasicSMSDto.APP_ID;
    public static final String PARAM_APP_ID = WebParamUtil.PARAM_PREFIX + BasicSMSDto.APP_ID;
    //sms code;
    public static final String PHONE = "phone";
    public static final String CODE = BasicSMSDto.SMS_CODE;
    public static final String HEADER_SMS_CODE = WebParamUtil.HEADER_COOKIE_PREFIX + CODE;
    public static final String PARAM_SMS_CODE = WebParamUtil.PARAM_PREFIX + CODE;
    public static final String CHANNEL = "channel";
    public static final String IS_CREATE = "isCreate";
    public static final String PARAM_IS_CREATE = WebParamUtil.PARAM_PREFIX + IS_CREATE;
    public static final String HEADER_IS_CREATE = WebParamUtil.HEADER_COOKIE_PREFIX + IS_CREATE;
    public static final String PARAM_CHANNEL = WebParamUtil.PARAM_PREFIX + CHANNEL;
    public static final String HEADER_CHANNEL = WebParamUtil.HEADER_COOKIE_PREFIX + CHANNEL;
    //open id;
    public static final String OPEN_ID = "openId";
    public static final String PARAM_OPEN_ID = WebParamUtil.PARAM_PREFIX + OPEN_ID;
    public static final String HEADER_OPEN_ID = WebParamUtil.HEADER_COOKIE_PREFIX + OPEN_ID;

    // public param
    public static final String USER_TYPE = "type";
    public static final String PARAM_USER_TYPE = WebParamUtil.PARAM_PREFIX + USER_TYPE;
    public static final String HEADER_USER_TYPE = WebParamUtil.HEADER_COOKIE_PREFIX + USER_TYPE;
    private static final String FALL_BACK_APP_ID = BasicSMSDto.FALLBACK_APP_ID;


    /**
     * find channel;
     *
     * @return
     */
    public static String getChannel() {
        return WebParamUtil.findWebParam(new WebParamUtil.WebParamObject(
                HEADER_CHANNEL, PARAM_CHANNEL, false
        ));
    }

    /**
     * @param throwEx
     * @return appId, to separate different app or end,if not present,a default appId is used;
     */
    public static String getAppId(boolean throwEx) {
        try {
            return WebParamUtil.findWebParam(new WebParamUtil.WebParamObject(HEADER_APP_ID, PARAM_APP_ID, throwEx));
        } catch (Exception e) {
            throw new InvalidRequestException(AuthError.REQUIRED_APP_ID.getMsg());
        }
    }

    /**
     * find smsCode request parameter value
     *
     * @param throwEx
     * @return smsCode
     * @throws InvalidRequestException
     */
    public static String getSmsCode(boolean throwEx) throws InvalidRequestException {
        try {
            return WebParamUtil.findWebParam(new WebParamUtil.WebParamObject(HEADER_SMS_CODE, PARAM_SMS_CODE
                    , throwEx));
        } catch (Exception e) {
            throw new InvalidRequestException(AuthError.REQUIRED_SMS_CODE.getMsg());
        }
    }

    /**
     * find user type request parameter value
     *
     * @return user type
     * @throws InvalidRequestException
     */
    public static String getUserType() throws InvalidRequestException {
        try {
            return WebParamUtil.findWebParam(new WebParamUtil.WebParamObject(HEADER_USER_TYPE, PARAM_USER_TYPE, true));
        } catch (Exception e) {
            throw new InvalidRequestException(AuthError.REQUIRED_USER_TYPE.getMsg());
        }
    }

    /**
     * @return clientId if the client is authenticated or fallback to {@link AuthParamUtils#getAppId(boolean)} if not;
     * @throws AuthenticationException
     */
    public static String getClientId() throws AuthenticationException {
        Authentication client = SecurityContextHolder.getContext().getAuthentication();
        if (!client.isAuthenticated() || AnonymousAuthenticationToken.class.isAssignableFrom(client.getClass())) {
            return getAppId(false);
        }
        String clientId = client.getName();
        if (client instanceof OAuth2Authentication) {
            // Might be a client and user combined authentication
            clientId = ((OAuth2Authentication) client).getOAuth2Request().getClientId();
        }
        return clientId;
    }

    /**
     * check if the request has the smscode
     *
     * @return
     */
    public static boolean hasSMSCode() {
        return Optional.ofNullable(getSmsCode(false)).isPresent();
    }

    /**
     * check if the request has the appId or clientId;
     *
     * @return
     */
    public static boolean hasAppId() {
        return Optional.ofNullable(getClientId()).isPresent();
    }

    /**
     * find openId
     *
     * @param throwEx
     * @return
     */
    public static String getOpenId(boolean throwEx) {
        try {
            return WebParamUtil.findWebParam(new WebParamUtil.WebParamObject(HEADER_OPEN_ID, PARAM_OPEN_ID
                    , throwEx));
        } catch (Exception e) {
            throw new InvalidRequestException(AuthError.REQUIRED_OPEN_ID.getMsg());
        }
    }

    /**
     * check openId is exists;
     *
     * @return
     */
    public static boolean hasOpenId() {
        return Optional.ofNullable(getOpenId(false)).isPresent();
    }

    /**
     * check password is exists
     *
     * @return
     */
    public static boolean hasPassword() {
        return Optional.ofNullable(WebParamUtil.findWebParam(new WebParamUtil.WebParamObject(
                UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY,
                UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, false
        ))).isPresent();
    }

    /**
     * <li>  find is create param ,default is {@code true};
     * <li>  a condition to create {@link User} by phone, when user is not exists
     *
     * @return
     */
    public static boolean smsIsCreate() {
        try {
            String p = WebParamUtil.findWebParam(new WebParamUtil.WebParamObject(HEADER_IS_CREATE, PARAM_IS_CREATE, false));
            return StringUtils.isEmpty(p) ? true : Boolean.valueOf(p);
        } catch (Exception e) {
            return true;
        }
    }
}
