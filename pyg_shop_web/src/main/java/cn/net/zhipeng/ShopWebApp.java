package cn.net.zhipeng;

import cn.net.zhipeng.shop.properties.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "cn.net.zhipeng.shop")
@EnableConfigurationProperties(SecurityProperties.class)
public class ShopWebApp {
    public static void main(String[] args) {
        SpringApplication.run(ShopWebApp.class, args);
    }
}
