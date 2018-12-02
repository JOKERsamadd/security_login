package cn.net.zhipeng.shop.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "zhipeng.security")
@Getter
@Setter
public class SecurityProperties {

    private SmsProperties sms;

    private QQProperties qq;
}
