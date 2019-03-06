/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.springcloud.service.auth.security.endpoint;

import com.springcloud.autoconfig.security.SecurityExceptionCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by yantao04 on 18/9/4.
 *
 * @author liubo
 * @author yantao04
 */
@Api(tags = "token有效性管理")
@FrameworkEndpoint
@ResponseBody
public class TokenInvalidEndpoint {


    private TokenStore tokenStore;

    @Autowired
    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @ApiOperation(value = "使指定的token失效")
    @RequestMapping(value = "/oauth/token/invalid", method = RequestMethod.POST)
    public ResponseEntity<?> invalidAccessToken(@RequestBody Map<String, Object> tokenMap) {
        try {
            String token = MapUtils.getString(tokenMap, "token");
            if (StringUtils.isNotEmpty(token)) {
                OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);//invoke to confirmed is a valid token
                if (oAuth2AccessToken != null) {
                    tokenStore.removeAccessToken(oAuth2AccessToken);
                    tokenStore.removeRefreshToken(oAuth2AccessToken.getRefreshToken());
                }
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new InvalidTokenException(SecurityExceptionCodeEnum.invalid_token.getMsg());
        }
    }

    @ApiOperation(value = "当前用户登出")
    @RequestMapping(value = "/oauth/logout")
    public ResponseEntity<?> invalidAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            try {
                String token = details.getTokenValue();
                OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
                if (oAuth2AccessToken != null) {
                    tokenStore.removeAccessToken(oAuth2AccessToken);
                    tokenStore.removeRefreshToken(oAuth2AccessToken.getRefreshToken());
                }
            } catch (InvalidTokenException e) {
                throw new InvalidTokenException(SecurityExceptionCodeEnum.invalid_token.getMsg());
            }
        }
        return ResponseEntity.ok().build();

    }
}
