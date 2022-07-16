package com.example.server.config.security.conponent;


import com.example.server.pojo.Menu;
import com.example.server.pojo.Role;
import com.example.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 根据请求url判断请求所需角色
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {


    @Autowired
    private IMenuService iMenuService;//注入刚刚的server

    AntPathMatcher antPathMatcher=new AntPathMatcher();//是否匹配的类吧

    @Override
    public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException {
        //获取请求的url
        String requestUrl = ((FilterInvocation) obj).getRequestUrl();

        List<Menu> menusWithRole = iMenuService.getMenusWithRole();
        //根据请求url与菜单角色是否匹配
        for (Menu menu : menusWithRole) {
            if (antPathMatcher.match(menu.getUrl(),requestUrl)){

                //根据JDK8的特性 俺到现在也没研究明白为何拿到数组
                String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                    //如果匹配上就把角色全部放在LIST
                return SecurityConfig.createList(str);

            }

        }
        //没匹配的url给个默认登录角色即可访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
