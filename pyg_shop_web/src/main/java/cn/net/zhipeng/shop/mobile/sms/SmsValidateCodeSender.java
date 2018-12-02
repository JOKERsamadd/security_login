package cn.net.zhipeng.shop.mobile.sms;

public interface SmsValidateCodeSender {

    void send(String mobile, String code);
}
