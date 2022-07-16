package com.example.server.config.security.conponent;


import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 判断用户角色和权限控制
 */

@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection)
            throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : collection) {
            //当前url所需要的角色
            String needRole = configAttribute.getAttribute();
            //判断它是否是登录即可访问的角色 ROLE_LOGIN时之前在拦截器里设置过登录角色
            if ("ROLE_LOGIN".equals(needRole)){
                //再去判断它是否登录
                if (authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("尚未登录，请登录");
                }else {
                    return;
                }
            }
            //判断用户角色是否为URL所需要的角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(needRole)){
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足 请联系管理员");
        //判断用户角色完活去配置安全框架
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
