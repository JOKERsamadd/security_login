package cn.net.zhipeng.shop.mobile.sms;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {

    ValidateCode generate(ServletWebRequest request);
}
