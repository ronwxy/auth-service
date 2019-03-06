package com.springcloud.api.auth.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(BaseClient.AUTH_SERVICE_NAME)
public interface LoginClient {

    /**<pre>
     * basicAuthorization;
     * <li>Authorization: Basic base64(clientId+":"+clientSecret)</li>
     * request param;
     * <li>login by username/password,{@code request}=[username,password,type,grant_type]</li>
     * <li>login by phone/password,{@code request}=[username,password,type,grant_type]</li>
     * <li>login by phone/code,{@code request}=[username,type,grant_type,ax_code]</li>
     *</pre>
     * @param basicAuthorization
     * @param request
     * @return
     * @see
     *
     *
     */
    @RequestMapping(value = "oauth/token", method = RequestMethod.POST)
    Map<String, Object> login(@RequestHeader(HttpHeaders.AUTHORIZATION)
                                      String basicAuthorization, @RequestBody Map<String, Object> request);

    /**
     * make the token invalid
     * @param req
     */
    @RequestMapping(value = "oauth/token/invalid",method = RequestMethod.POST)
    void tokenInvalid(@RequestBody Map<String,Object> req);
}
