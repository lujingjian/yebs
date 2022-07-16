package com.example.server.controller;


import com.example.server.pojo.Menu;
import com.example.server.service.IAdminService;
import com.example.server.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
@RestController
@RequestMapping("/system/config")//因为到时候会根据url进行一个过滤拦截 所以把路径设置跟表里url一样方便代码
public class MenuController {

    @Autowired
    private IMenuService MenuService;

    @ApiOperation(value ="通过用户查菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenusByAdminId(){


        return MenuService.getMenuByAdminId();
    }


}
