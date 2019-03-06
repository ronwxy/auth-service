package com.springcloud.service.auth.security.endpoint;

import com.springcloud.service.auth.service.base.IUserService;
import com.springcloud.service.auth.util.AuthParamUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * make the oauth2 token endpoint support {@link MediaType#APPLICATION_JSON_UTF8}
 */
public class RequestBodyTokenEndpoint extends TokenEndpoint {

    @Autowired
    IUserService userService;

    @RequestMapping(value = "/oauth/token", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OAuth2AccessToken> requestBodyAccessToken(Principal principal, HttpServletRequest request,
                                                                    @RequestBody Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        adaptInternalAttributes(request, parameters);

        return super.postAccessToken(principal, parameters);
    }

    /**
     *
     * @param request
     * @param parameters
     */
    protected void adaptInternalAttributes(HttpServletRequest request, @RequestBody Map<String, String> parameters) {
        request.setAttribute(AuthParamUtils.PARAM_USER_TYPE, MapUtils.getString(parameters, AuthParamUtils.PARAM_USER_TYPE));
        request.setAttribute(AuthParamUtils.PARAM_APP_ID, MapUtils.getString(parameters, AuthParamUtils.PARAM_APP_ID));
        request.setAttribute(AuthParamUtils.PARAM_SMS_CODE, MapUtils.getString(parameters, AuthParamUtils.PARAM_SMS_CODE));
        request.setAttribute(AuthParamUtils.PARAM_CHANNEL, MapUtils.getString(parameters, AuthParamUtils.PARAM_CHANNEL));
        request.setAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY,
                MapUtils.getString(parameters, UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY));
    }
}
