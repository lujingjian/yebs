package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.Admin;
import com.example.server.pojo.Menu;
import com.example.server.pojo.RestBean;
import com.example.server.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  登录之后返回TOken
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
public interface IAdminService extends IService<Admin> {

    RestBean login(String username, String password, String code, HttpServletRequest request);

        /*根据用户名获取用户*/
    Admin getAdminByUserName(String username);

    //根据用户ID查询角色
    List<Role> getRole(Integer adminId);

    //获取所有操作员
    List<Admin> getAllAdmin( String keywords);

    //获取更新员角色
    RestBean upRole(Integer adminId, Integer[] ids);
}
