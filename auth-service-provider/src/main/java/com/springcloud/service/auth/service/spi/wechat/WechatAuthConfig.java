package com.springcloud.service.auth.service.spi.wechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.web.servlet.View;

import javax.sql.DataSource;

@Profile("prod")
@Configuration
public class WechatAuthConfig extends SocialConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ConnectionSignUp connectionSignUp;

    @Value("wechat.app-id")
    private String wechatAppId;

    @Value("wechat.app-secret")
    private String wechatAppSecret;
    @Value("wechat.provider-id")
    private String wechatProviderId;

    protected ConnectionFactory<?> createConnectionFactory() {
        return new WechatConnectionFactory(wechatProviderId, wechatAppId, wechatAppSecret);
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        InMemoryUsersConnectionRepository inMemoryUsersConnectionRepository =
                new InMemoryUsersConnectionRepository(connectionFactoryLocator);
        inMemoryUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
        //---jdbc
        return inMemoryUsersConnectionRepository;
    }
    @Bean({"connect/weixinConnect", "connect/weixinConnected"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    public View weixinConnectedView() {
        return new SocialConnectView();
    }
}
