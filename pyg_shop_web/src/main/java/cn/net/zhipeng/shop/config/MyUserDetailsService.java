package cn.net.zhipeng.shop.config;

import cn.net.zhipeng.pojo.Seller;
import cn.net.zhipeng.sellergoods.service.SellerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

    @Reference
    private SellerService sellerService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("商家表单登录用户名：" + username);
        Seller seller = sellerService.findOne(username);
        if (seller != null && "1".equals(seller.getStatus())) {
            return new User(username, seller.getPassword(),
                    AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_SELLER"));
        }
        return new User(username, seller != null ? seller.getPassword() : "",
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_SELLER"));
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        return new SocialUser(userId, "",
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_SELLER"));
    }
}
