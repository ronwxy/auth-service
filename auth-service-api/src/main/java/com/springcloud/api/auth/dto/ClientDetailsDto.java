package com.springcloud.api.auth.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Accessors(chain = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class ClientDetailsDto implements Serializable {
    private Long id;
    private String clientId;

    private String clientSecret;
    private Set<String> scope;
    private Set<String> resourceIds;
    private Set<String> authorizedGrantTypes;
    private Set<String> registeredRedirectUris;

    private Set<String> autoApproveScopes;


    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;

    private Map<String, Object> additionalInformation;

    private List<String> authorities;
}
