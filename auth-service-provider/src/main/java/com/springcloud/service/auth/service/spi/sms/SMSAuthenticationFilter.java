package com.springcloud.service.auth.service.spi.sms;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * sms filter for sms login
 * @author liubo
 */
public class SMSAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final boolean postOnly = true;
    public SMSAuthenticationFilter(){
        super(new AntPathRequestMatcher("/login/sms", HttpMethod.POST.name()));
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(postOnly&&!request.getMethod().equalsIgnoreCase(HttpMethod.POST.name())){
            throw new AuthenticationServiceException("authorize method not support:" + request.getMethod());
        }
        String mobile = obtainMobile(request);
        if (mobile == null) {
            mobile = "";
        }
        mobile = mobile.trim();
        SMSAuthenticationToken authRequest = new SMSAuthenticationToken(mobile);
        setDetails(request,authRequest);
        return getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, SMSAuthenticationToken authRequest) {
        //--
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    private String obtainMobile(HttpServletRequest request) {
        return request.getParameter("mobile");
    }
}
