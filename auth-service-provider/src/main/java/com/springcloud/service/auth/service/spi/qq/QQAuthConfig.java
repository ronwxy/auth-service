package com.springcloud.service.auth.service.spi.qq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;

import javax.sql.DataSource;

@Profile("prod")
@Configuration
public class QQAuthConfig extends SocialConfigurerAdapter {
    protected ConnectionFactory<?> createConnectionFactory() {
        return new QQConnectFactory(qqProviderId, qqAppId, qqAppSecret);
    }
    //------qq配置
    @Value("${qq.provider-id:xxx}")
    private String qqProviderId;
    @Value("${qq.app-id:xxx}")
    private String qqAppId;
    @Value("${qq.app-secret:xxx}")
    private String qqAppSecret;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ConnectionSignUp connectionSignUp;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        //------inMemory
        InMemoryUsersConnectionRepository repository =
                new InMemoryUsersConnectionRepository(connectionFactoryLocator);
        repository.setConnectionSignUp(connectionSignUp);
        //------jdbc
      /*  JdbcUsersConnectionRepository jdbcRepository =
                new JdbcUsersConnectionRepository(dataSource,connectionFactoryLocator, Encryptors.noOpText());
        if (jdbcRepository
                != null) {
            jdbcRepository.setConnectionSignUp(connectionSignUp);

        }*/
            return repository;
    }
}
