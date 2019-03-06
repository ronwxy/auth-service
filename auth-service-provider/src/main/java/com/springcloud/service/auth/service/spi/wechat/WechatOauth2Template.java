package com.springcloud.service.auth.service.spi.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class WechatOauth2Template extends OAuth2Template {
    private static final Logger logger = LoggerFactory.getLogger(WechatOauth2Template.class);
    private static final String REFRESH_TOKEN_URL = "https://controller.weixin.qq.com/sns/oauth2/refresh_token";
    @Value("${wechat.client-id}")
    private String clientId;
    @Value("${wechat.client-secret}")
    private String clientSecret;
    @Value("${wechat.access-authorize-url")
    private String accessTokenUrl;
    @Value("${wechat.refresh-authorize-url}")
    private String refreshTokenUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    public WechatOauth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
    }

    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
        StringBuilder builder = new StringBuilder(accessTokenUrl);
        builder.append("?appId=" + clientId);
        builder.append("&secret=" + clientSecret);
        builder.append("&code" + authorizationCode);
        builder.append("&grant_type=authorization_code");
        builder.append("&redirect_uri=" + redirectUri);
        return getAccessToken(builder);
    }

    @Override
    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {
        StringBuilder builder = new StringBuilder(this.refreshTokenUrl);
        builder.append("?appid=" + clientId);
        builder.append("&grant_type=refresh_token");
        builder.append("&refresh_token=" + refreshToken);

        return getAccessToken(builder);
    }

    private AccessGrant getAccessToken(StringBuilder builder) {
        logger.info("to get access authorize ,url:" + builder);
        String response = getRestTemplate().getForObject(builder.toString(), String.class);
        logger.info("get access authorize,response:" + response);
        Map<String, Object> result = null;
        try {
            result = objectMapper.readValue(response, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("extract result failed", e);
        }
        Object errCode = result.get("errcode");
        Object errMsg = result.get("errmsg");
        if (!StringUtils.isEmpty(errCode)) {
            throw new RuntimeException(String.format("get access authorize failed,errCode=%s,errMsg=%s", errCode, errMsg));
        }
        Object accessToken = result.getOrDefault("access_token", "");
        Object scope = result.getOrDefault("scope", "");
        Object refreshToken = result.getOrDefault("refresh_token", "");
        Object expiresIn = result.getOrDefault("expires_in", 0L);
        Object openId = result.getOrDefault("openid", "");
        WechatAccessGrant wechatAccessGrant = new WechatAccessGrant(
                accessToken.toString(),
                scope.toString(),
                refreshToken.toString(),
                (Long) expiresIn
        );
        wechatAccessGrant.setOpenId(openId.toString());
        return wechatAccessGrant;
    }

    @Override
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        String url = super.buildAuthenticateUrl(parameters);
        url = url + "&appid=" + clientId + "&scope=snsapi_login";
        return url;
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate template = super.createRestTemplate();
        List<HttpMessageConverter<?>> converters = template.getMessageConverters();
        converters.remove(0);
        converters.add(new StringHttpMessageConverter(Charset.defaultCharset()));
        return template;
    }
}
