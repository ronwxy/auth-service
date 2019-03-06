package com.springcloud.service.auth.domain;

import com.springboot.autoconfig.tkmapper.domain.FixedIdBaseDomain;
import com.springboot.autoconfig.tkmapper.typehandlers.JsonTypeHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;

@Table(name = "oauth_client_details")
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class OAuth2ClientDetails extends FixedIdBaseDomain<Long> {

    private String clientId;

    private String clientSecret;
    @ColumnType(typeHandler = JsonTypeHandler.class)
    private List<String> scope;
//    @ColumnType(typeHandler = JsonTypeHandler.class)
    private List<String> resourceIds;
//    @ColumnType(typeHandler = JsonTypeHandler.class)
    private List<String> authorizedGrantTypes;
    @Column(name = "web_server_redirect_uri")
//    @ColumnType(typeHandler = JsonTypeHandler.class)
    private List<String> registeredRedirectUris;
    @Column(name = "autoapprove")
//    @ColumnType(typeHandler = JsonTypeHandler.class)
    private List<String> autoApproveScopes;

    @Column(name = "access_token_validity")
    private Integer accessTokenValiditySeconds;
    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValiditySeconds;

//    @ColumnType(typeHandler = JsonTypeHandler.class)
    private Map<String, Object> additionalInformation;
//    @ColumnType(typeHandler = JsonTypeHandler.class)
    private List<String> authorities;

}
