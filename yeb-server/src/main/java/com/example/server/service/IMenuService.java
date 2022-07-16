package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
public interface IMenuService extends IService<Menu> {

    //根据用户ID查菜单列表
    List<Menu> getMenuByAdminId();

    //根据角色获取菜单列表
   List<Menu> getMenusWithRole();


    //查询所有菜单
    List<Menu> getAllmenus();


}
