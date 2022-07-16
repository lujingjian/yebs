package com.example.server.config.utils;

import com.example.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * <获取当前登录用户
 *
 * @Date
 */
@Component
public class adminUtil {

    public static Admin getLoginAdmin(){
        //* <获取当前登录用户
         Admin principa = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principa);

        return  (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
