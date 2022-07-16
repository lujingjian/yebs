package com.example.server.controller;

import com.example.server.pojo.Admin;
import com.example.server.pojo.AdminLoginParam;
import com.example.server.pojo.AdminRole;
import com.example.server.pojo.RestBean;
import com.example.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@Api(tags = "loginController")
public class loginController {


    @Autowired
    private IAdminService iAdminService; //注入service

    @ApiOperation(value = "登录之后返回TOKEN")
    @PostMapping("/login")
    public RestBean login(@RequestBody AdminLoginParam adminLoginParam,
                          HttpServletRequest request){
        return iAdminService.login(adminLoginParam.getUsername(),
                adminLoginParam.getPassword(),adminLoginParam.getCode(),request); //getcode添加了图片验证方法out+ent 让他们的接口和实现类都添加getCode

    }
        @ApiOperation(value = "获取当前用户的信息")
        @GetMapping("/admin/info")
        public Admin getadminInfo(Principal principal){//Principal类是之前放入全局才能
        if (null==principal){
            return null;
        }
        String username=principal.getName();
        Admin admin=iAdminService.getAdminByUserName(username);//通过业务层去获取完整的用户名
            admin.setPassword(null);//密码不能泄露设置空

            admin.setRoles(iAdminService.getRole(admin.getId()));//这样到时候我们返回的用户信息的时候就有我们的角色
            return admin;
        }

    //前端处理退出功能 后端只要返回给他一个成功的接口就好了
    @ApiOperation(value = "退出功能")
    @PostMapping("/logout")
    public RestBean logout(){

        return RestBean.success("退出成功!");
    }
}
