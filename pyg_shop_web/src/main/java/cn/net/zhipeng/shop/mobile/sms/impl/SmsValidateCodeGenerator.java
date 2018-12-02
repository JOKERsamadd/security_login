package cn.net.zhipeng.shop.mobile.sms.impl;

import cn.net.zhipeng.shop.mobile.sms.ValidateCode;
import cn.net.zhipeng.shop.mobile.sms.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(6);
        return new ValidateCode(code, 60*5);
    }
}
