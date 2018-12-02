package cn.net.zhipeng.shop.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsProperties {

    private String accessKeyId;

    private String accessKeySecret;

    private String templateCode;
}
