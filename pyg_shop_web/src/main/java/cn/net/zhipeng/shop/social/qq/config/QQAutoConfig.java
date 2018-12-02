package cn.net.zhipeng.shop.social.qq.config;

import cn.net.zhipeng.shop.social.qq.connect.QQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;

@Configuration
//@ConditionalOnProperty(prefix = "zhipeng.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialConfigurerAdapter {


    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final String PROVIDER_ID = "qq";
    public static final String APP_ID = "101517573";
    public static final String APP_SECRET = "ba58262e5d7441ad3ed4f9603948b880";

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        logger.info("第三方认证自动配置开启：创建连接工厂ing...");
        connectionFactoryConfigurer.addConnectionFactory(new QQConnectionFactory(PROVIDER_ID, APP_ID, APP_SECRET));
    }

/*    @Bean({"connect/qqConnect", "connect/qqConnected"})
    @ConditionalOnMissingBean(name = "qqConnectedView")
    public View qqConnectedView() {
        return new MyConnectView();
    }*/
}
