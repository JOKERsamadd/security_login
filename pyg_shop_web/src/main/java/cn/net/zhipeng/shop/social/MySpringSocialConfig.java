package cn.net.zhipeng.shop.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class MySpringSocialConfig extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    public MySpringSocialConfig(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter socialAuthenticationFilter = (SocialAuthenticationFilter) super.postProcess(object);
        socialAuthenticationFilter.setFilterProcessesUrl(filterProcessesUrl);
        socialAuthenticationFilter.setPostLoginUrl("/admin/index.html");
        return (T) socialAuthenticationFilter;
    }
}
