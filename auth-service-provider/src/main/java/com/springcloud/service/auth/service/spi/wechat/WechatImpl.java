package com.springcloud.service.auth.service.spi.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class WechatImpl extends AbstractOAuth2ApiBinding implements WeChat {

    private static final String WEIXIN_URL_GET_USER_INFO = "https://controller.weixin.qq.com/sns/userinfo?openid=";

    @Value("${wechar.user-info-url:#{WEIXIN_URL_GET_USER_INFO}}")
    private String wecharUserInfoUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    public WechatImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }
    @Override
    public WechatUserInfo getUserInfo(String openId) {
            String url = this.wecharUserInfoUrl + openId;
        String result = getRestTemplate().getForObject(url, String.class);
        if(StringUtils.hasText(result) && result.indexOf("errcode")<0){
            return  null;
        }
        WechatUserInfo wechatUserInfo = null;

        try {
            wechatUserInfo = objectMapper.readValue(result, WechatUserInfo.class);

        } catch (IOException e) {
            throw new RuntimeException("get wechat user info failed", e);
        }
        return wechatUserInfo;
    }

    /**
     * change stringHttpMessageConvert's charset from iso-8859-1 to utf8
     * @return
     */
    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> converters = super.getMessageConverters();
        converters.remove(0);
        converters.add(new StringHttpMessageConverter(Charset.defaultCharset()));
        return converters;
    }
}
