package com.example.server.config.security;

import com.example.server.config.security.conponent.CustomFilter;
import com.example.server.config.security.conponent.CustomUrlDecisionManager;
import com.example.server.config.security.conponent.RestAuthorizePoint;
import com.example.server.config.security.conponent.RestnotAccessHander;
import com.example.server.config.jwtAuthencationToken;
import com.example.server.pojo.Admin;
import com.example.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*配置类*/
@Configuration
public class Securitycofig extends WebSecurityConfigurerAdapter {
    @Autowired
    IAdminService adminService;
    @Autowired
    RestAuthorizePoint restAuthorizePoint;
    @Autowired
    RestnotAccessHander restnotAccessHander;

    @Autowired
    CustomFilter customFilter;

    @Autowired
    CustomUrlDecisionManager customUrlDecisionManager;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {//相当于重写了 userDetailsService.loadUserByUsername
        return username -> {
            Admin admin = adminService.getAdminByUserName(username);
            if (null != admin) {

                admin.setRoles(adminService.getRole(admin.getId()));//返回之前设置下 这下子登录以及获取用户信息都能返回角色列表
                //然后去添加拦截
                return admin;
            }
            throw new UsernameNotFoundException("登录错误 用户名密码错误！");//抛出异常
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();//框架里具体的密码实现的方法
    }

    @Bean
    public jwtAuthencationToken jwtAuthencationToken() {

        return new jwtAuthencationToken();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(//放行一些路径 不走拦截链的
                "/login",
                "/logout",
                "/css/**",
                "/js",
                "/index.html",
                "/favicon.ico",
                "/doc.html",
                "/webjars/**",//必须放行的 不然光放doc没有用
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha",
                "/ws/**",
                "/jk.html",
                "/system/config",
                "/menu",
                "/system/config/menu"
        );
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //使用JWT，不需要csrf
        http.csrf()
                .disable()
                //基于token，不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //除上面void configure方法外，所有请求都要求认证
                .anyRequest()
                .authenticated()
                //动态权限配置 俺到现在也没搞明白这是什么牛马 嘤嘤嘤
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O obj) {
                        obj.setAccessDecisionManager(customUrlDecisionManager);
                        obj.setSecurityMetadataSource(customFilter);
                        return obj;
                    }
                })
                .and()
                //禁用缓存
                .headers()
                .cacheControl();

        /*添加jwt登录授权过滤器*/
        http.addFilterBefore(jwtAuthencationToken(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restnotAccessHander)
                .authenticationEntryPoint(restAuthorizePoint);

    }

}