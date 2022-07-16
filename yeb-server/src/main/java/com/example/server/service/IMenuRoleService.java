package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.MenuRole;
import com.example.server.pojo.RestBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
public interface IMenuRoleService extends IService<MenuRole> {



    //更新角色菜单
    RestBean updateRole(Integer rid, Integer[] mids);

   /* //更新角色菜单
    RestBean updateRole(Integer rid, Integer[] mids);*/
}
