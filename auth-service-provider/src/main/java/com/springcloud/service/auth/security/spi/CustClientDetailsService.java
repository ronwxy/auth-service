package com.springcloud.service.auth.security.spi;

import com.springcloud.service.auth.domain.OAuth2ClientDetails;
import com.springcloud.service.auth.service.base.ICustClientDetailsService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Objects;

import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;

/**
 * feign impl
 */
public class CustClientDetailsService implements ClientDetailsService {
    private ICustClientDetailsService clientDetailsService;

    public void setClientDetailsService(ICustClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OAuth2ClientDetails OAuth2ClientDetails = clientDetailsService.findClientByClientId(clientId);
        if (Objects.isNull(OAuth2ClientDetails)) {
            throw new ClientRegistrationException("client not exists");
        }
        BaseClientDetails clientDetails = new BaseClientDetails(OAuth2ClientDetails.getClientId(),
                collectionToCommaDelimitedString(OAuth2ClientDetails.getResourceIds()),
                collectionToCommaDelimitedString(OAuth2ClientDetails.getScope()),
                collectionToCommaDelimitedString(OAuth2ClientDetails.getAuthorizedGrantTypes()),
                collectionToCommaDelimitedString(OAuth2ClientDetails.getAuthorities()),
                collectionToCommaDelimitedString(OAuth2ClientDetails.getRegisteredRedirectUris()));
        clientDetails.setClientSecret(OAuth2ClientDetails.getClientSecret());
        clientDetails.setAccessTokenValiditySeconds(OAuth2ClientDetails.getAccessTokenValiditySeconds());
        clientDetails.setRefreshTokenValiditySeconds(OAuth2ClientDetails.getRefreshTokenValiditySeconds());
        return clientDetails;
    }
}
