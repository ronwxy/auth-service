package com.springcloud.service.auth.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.springboot.autoconfig.error.exception.BizException;
import com.springboot.autoconfig.error.exception.ExceptionUtil;
import com.springcloud.service.auth.error.AuthError;
import com.springcloud.service.auth.security.sms.ISMSCodeService;
import com.springcloud.service.auth.util.AuthParamUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * login controller wrap {@link TokenEndpoint}
 *
 */
@RestController
@RequestMapping("login")
public class LoginController {

    public static final String default_grant_type = "password";
    private static final String p_type = AuthParamUtils.USER_TYPE;
    private static final String p_grantType = "grantType";
    //password login
    private static final String p_username = AuthParamUtils.USERNAME;
    private static final String p_password = AuthParamUtils.PASSWORD;

    //sms login
    private static final String p_phone = AuthParamUtils.PHONE;
    private static final String p_code = AuthParamUtils.CODE;
    private static final String p_channel = AuthParamUtils.CHANNEL;
    private static final String p_create = AuthParamUtils.IS_CREATE;

    //openId login
    private static final String p_openId = "openId";
    @Autowired
    private TokenEndpoint tokenEndpoint;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ISMSCodeService smsCodeService;


    @RequestMapping(value = "password", method = {RequestMethod.POST})
    public Map<String, Object> passwordLogin(HttpServletRequest request, Principal principal, @RequestBody Map<String, String> req) throws HttpRequestMethodNotSupportedException {
        String username = MapUtils.getString(req, p_username);
        String password = MapUtils.getString(req, p_password);
        String type = MapUtils.getString(req, p_type);
        String grantType = MapUtils.getString(req, p_grantType);
        if (StringUtils.isEmpty(username)) {
            ExceptionUtil.rethrowClientSideException(AuthError.REQUIRED_USERNAME);
        }
        if (StringUtils.isEmpty(password)) {
            ExceptionUtil.rethrowClientSideException(AuthError.REQUIRED_PASSWORD);
        }
        if (StringUtils.isEmpty(type)) {
            ExceptionUtil.rethrowClientSideException(AuthError.REQUIRED_USER_TYPE);
        }
        if (StringUtils.isEmpty(grantType)) {
            req.put(p_grantType, default_grant_type);//password type is default;
        }
        Map<String, String> endpointParam = doPasswordLoginConvert(req, request);
        ResponseEntity<OAuth2AccessToken> tokenEntity = tokenEndpoint.postAccessToken(principal, endpointParam);
        //do wrapper;
        return doTokenWrapper(tokenEntity);
    }

    /**
     * *<li> convert login param to {@link TokenEndpoint} param;
     * <li> change param name from {@link #p_grantType} to {@link OAuth2Utils#GRANT_TYPE}
     * <li> set {@link AuthParamUtils#PARAM_USER_TYPE, AuthParamUtils#PASSWORD} attribute to {@link HttpServletRequest} to support {@link AuthParamUtils}
     *
     * @param req
     * @param request
     * @return
     */
    protected Map<String, String> doPasswordLoginConvert(Map<String, String> req, HttpServletRequest request) {
        Map<String, String> result = new HashMap<>(req);
        result.put(AuthParamUtils.PARAM_USER_TYPE, MapUtils.getString(req, p_type));
        result.put(OAuth2Utils.GRANT_TYPE, MapUtils.getString(req, p_grantType));
        request.setAttribute(AuthParamUtils.PASSWORD, MapUtils.getString(req, p_password));
        request.setAttribute(AuthParamUtils.PARAM_USER_TYPE, MapUtils.getString(req, p_type));
        return result;
    }

    protected Map<String, Object> doTokenWrapper(ResponseEntity<OAuth2AccessToken> tokenEntity) {
        //default wrapper ,do nothing;
        return objectMapper.convertValue(tokenEntity.getBody(), Map.class);
    }

    @RequestMapping(value = "sms", method = RequestMethod.POST)
    public Map<String, Object> smsLogin(HttpServletRequest request, Principal principal, @RequestBody Map<String, String> req) throws HttpRequestMethodNotSupportedException {
        String phone = MapUtils.getString(req, p_phone);
        String code = MapUtils.getString(req, p_code);
        String type = MapUtils.getString(req, p_type);
        String grantType = MapUtils.getString(req, p_grantType);
        String channel = MapUtils.getString(req, p_channel);
        Boolean create = MapUtils.getBoolean(req, p_create);
        if (StringUtils.isEmpty(phone)) {
            ExceptionUtil.rethrowClientSideException(AuthError.REQUIRED_PHONE);
        }
        if (StringUtils.isEmpty(code)) {
            ExceptionUtil.rethrowClientSideException(AuthError.REQUIRED_SMS_CODE);
        }
        if (StringUtils.isEmpty(type)) {
            ExceptionUtil.rethrowClientSideException(AuthError.REQUIRED_USER_TYPE);
        }
        if (StringUtils.isEmpty(grantType)) {
            req.put(p_grantType, default_grant_type);//password type is default;
        }
        if (Objects.isNull(create)) {
            //default create user;
            req.put(p_create, Boolean.TRUE.toString());
        }
        //do sms check
        try {
            smsCodeService.verifySMSCode(AuthParamUtils.getClientId(), phone, code);
        } catch (HystrixBadRequestException e) {
            BizException be = (BizException) e.getCause();
            if (be == null) {
                throw e;
            }
            throw new HystrixBadRequestException(be.getErrorMessage(),
                    new BizException(HttpStatus.UNAUTHORIZED, be.getErrorCode(), be.getMessage()));
        }

        Map<String, String> endpointParam = doSMSLoginConvert(req, request);
        ResponseEntity<OAuth2AccessToken> tokenResponseEntity = tokenEndpoint.postAccessToken(principal, endpointParam);
        return doTokenWrapper(tokenResponseEntity);
    }

    /**
     * <li> convert login param to {@link TokenEndpoint} param;
     * <li> set param name from {@link #p_phone} to {@link #p_username}
     * <li> change param name from {@link #p_grantType} to {@link OAuth2Utils#GRANT_TYPE}
     * <li><p>  set {@link AuthParamUtils#PARAM_USER_TYPE, AuthParamUtils#PARAM_CHANNEL, AuthParamUtils#PARAM_SMS_CODE, AuthParamUtils#PARAM_IS_CREATE}
     * attribute to {@link HttpServletRequest} to support {@link AuthParamUtils}
     *
     * @param req
     * @param request
     * @return
     */
    protected Map<String, String> doSMSLoginConvert(Map<String, String> req, HttpServletRequest request) {
        Map<String, String> result = new HashMap<>(req);
        result.put(p_username, MapUtils.getString(req, p_phone));
        result.put(OAuth2Utils.GRANT_TYPE, MapUtils.getString(req, p_grantType));

        request.setAttribute(AuthParamUtils.PARAM_USER_TYPE, MapUtils.getString(req, p_type));
        request.setAttribute(AuthParamUtils.PARAM_CHANNEL, MapUtils.getString(req, p_channel));
        request.setAttribute(AuthParamUtils.PARAM_SMS_CODE, MapUtils.getString(req, p_code));
        request.setAttribute(AuthParamUtils.PARAM_IS_CREATE, MapUtils.getString(req, p_create));

        return result;
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<OAuth2Exception> handleException(Exception e) throws Exception {
        return tokenEndpoint.handleException(e);
    }


    @RequestMapping(value = "openId", method = RequestMethod.POST)
    public Map<String, Object> openIdLogin(HttpServletRequest request, Principal principal, @RequestBody Map<String, String> req) throws HttpRequestMethodNotSupportedException {

        String openId = MapUtils.getString(req, p_openId);
        if (StringUtils.isEmpty(openId)) {
            ExceptionUtil.rethrowClientSideException(AuthError.REQUIRED_OPEN_ID);
        }
        String grantType = MapUtils.getString(req, p_grantType);
        if (StringUtils.isEmpty(grantType)) {
            req.put(p_grantType, default_grant_type);
        }
        Map<String, String> endpointParams = doOpenIdConvert(req, request);


        return doTokenWrapper(tokenEndpoint.postAccessToken(principal, endpointParams));

    }

    /**
     * <li>convert login param to {@link TokenEndpoint} param;
     * <li>change param name from  {@link #p_openId} to {@link #p_username}
     * <li>change param name from {@link this#p_grantType} to {@link OAuth2Utils#GRANT_TYPE};
     * <li>set {@link AuthParamUtils#OPEN_ID} attribute to {@link HttpServletRequest} to support {@link AuthParamUtils#getOpenId(boolean)}
     *
     * @param req
     * @param request
     * @return
     */
    protected Map<String, String> doOpenIdConvert(Map<String, String> req, HttpServletRequest request) {
        Map<String, String> result = new HashMap<>(req);
        result.put(p_username, MapUtils.getString(req, p_openId));
        result.put(OAuth2Utils.GRANT_TYPE, MapUtils.getString(req, p_grantType));
        request.setAttribute(AuthParamUtils.PARAM_OPEN_ID,
                MapUtils.getString(req, p_openId));
        return result;
    }
}
