package com.springcloud.service.auth.web.controller;

import com.springcloud.service.auth.security.endpoint.TokenInvalidEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * logout wrap for {@link TokenInvalidEndpoint}
 *
 * @author liubo
 */
@RestController
@RequestMapping("logout")
public class LogoutController {
    @Autowired
    private TokenInvalidEndpoint tokenInvalidEndpoint;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> logout() {
        return tokenInvalidEndpoint.invalidAccessToken();
    }

    @RequestMapping(value = "token", method = RequestMethod.POST)
    public ResponseEntity<?> tokenLogout(@RequestBody Map<String, Object> req) {
        return tokenInvalidEndpoint.invalidAccessToken(req);
    }
}
