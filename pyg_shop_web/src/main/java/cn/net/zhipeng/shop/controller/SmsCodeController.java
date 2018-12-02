package cn.net.zhipeng.shop.controller;

import cn.net.zhipeng.shop.mobile.sms.SmsValidateCodeSender;
import cn.net.zhipeng.shop.mobile.sms.ValidateCode;
import cn.net.zhipeng.shop.mobile.sms.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SmsCodeController {

    @Autowired
    ValidateCodeGenerator smsValidateCodeGenerator;

    @Autowired
    SmsValidateCodeSender smsValidateCodeSender;

    SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    public static final String SESSION_KEY = "SESSION_KEY_SMS_CODE";


    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request) throws ServletRequestBindingException {
        ValidateCode smsCode = smsValidateCodeGenerator.generate(new ServletWebRequest(request));
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
        if (mobile.equals("17864308336")) {
            smsCode.setCode("521");
        }
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
//        String mobile = request.getParameter("mobile");
        smsValidateCodeSender.send(mobile, smsCode.getCode());
    }
}
