package cn.net.zhipeng.shop.config;

import cn.net.zhipeng.shop.mobile.SmsCodeAuthenticationSecurityConfig;
import cn.net.zhipeng.shop.mobile.sms.SmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
//@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter  {

    @Autowired
    AuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    UserDetailsService myUserDetailsService;

    @Autowired
    DataSource dataSource;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    SpringSocialConfigurer mySpringSocialConfig;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        smsCodeFilter.afterPropertiesSet();

        http.addFilterAfter(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                    .loginPage("/shoplogin.html")
                    .loginProcessingUrl("/authentication/form")
                    .defaultSuccessUrl("/admin/index.html")
                    .failureHandler(myAuthenticationFailureHandler)
//                    .successHandler(myAuthenticationSuccessHandler)
//                    .failureForwardUrl("/shoplogin.html")
                    .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                    .and()
                .apply(mySpringSocialConfig)
                    .and()
                .rememberMe()
                    .tokenRepository(persistentTokenRepository(dataSource))
                    .tokenValiditySeconds(3600)
                    .userDetailsService(myUserDetailsService)
                    .and()
                .authorizeRequests()
                    .antMatchers("/code/sms","/*.html", "/css/**", "/img/**", "/js/**", "/plugins/**", "/seller")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                .headers()
                    .frameOptions()
                    .disable()
                    .and()
                .csrf().disable();
    }
}
