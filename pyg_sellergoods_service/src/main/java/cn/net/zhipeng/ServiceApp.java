package cn.net.zhipeng;


import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableDubbo
@MapperScan("cn.net.zhipeng.mapper")
public class ServiceApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceApp.class)
                .web(WebApplicationType.NONE).run(args);
    }
}
