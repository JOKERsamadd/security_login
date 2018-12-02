package cn.net.zhipeng.shop.social.qq.api;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;


/**
 * 从服务提供商获取用户信息
 * 处理获取令牌accessToken之后的逻辑
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ{

    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    //缺省的accessToken参数父类会帮忙添加
    private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
    // 申请第三方登录成功之后分配的appId
    private String appId;
    // 服务提供商那用户的唯一标识。
    private String openId; // 这两个参数与access_token一起构建成获取用户信息的请求

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 通过accessToken获取用户的openId
     * @param accessToken
     * @param appId
     */
    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        logger.info("获取用户openId：" + result);
        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    /**
     * 通过获取的openId，获取用户信息
     * @return
     */
    @Override
    public QQUserInfo getUserInfo(){
        String url = String.format(URL_GET_USER_INFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        logger.info("获取用户信息：" + result);
        try {
            QQUserInfo qqUserInfo = JSON.parseObject(result, QQUserInfo.class);
//                    objectMapper.readValue(result, QQUserInfo.class);
            assert qqUserInfo != null;
            qqUserInfo.setOpenId(openId);
            return qqUserInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }
}
