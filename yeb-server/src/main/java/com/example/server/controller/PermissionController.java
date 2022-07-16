package com.example.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.mapper.RoleMapper;
import com.example.server.pojo.*;
import com.example.server.service.IMenuRoleService;
import com.example.server.service.IMenuService;
import com.example.server.service.IRoleService;
import com.example.server.service.impl.RoleServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <权限组类
 *
 * @Date 2021/11/30<>15:02
 */
@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {

    @Autowired
    private IRoleService service;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IMenuRoleService menuRoleService;

    @ApiOperation(value = "查找所有角色")
    @GetMapping("/all")
    public List<Role> getRole() {

        return service.list();
    }


    @ApiOperation(value = "添加角色")
    @PostMapping("/role")
    public RestBean addRole(@RequestBody Role role) {
        //判断是否是这个开头
        if (!role.getName().startsWith("ROLE_")) {
            //如果不是就补充它
            role.setName("ROLE_"+role.getName());//拼接
        }
        if (service.save(role)){
            return RestBean.success("添加成功");
        }
        return RestBean.error("添加失败");
    }

    @ApiOperation(value = "单个删除角色")
    @DeleteMapping("/role/{rid}")
    public RestBean deleteRole(Integer rid){

        if (service.removeById(rid)) {
            return  RestBean.success("成功删除该选项");
        }
        return RestBean.error("删除失败");
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>》》》》》》
    @ApiOperation(value = "查询所有菜单 ")
    @GetMapping("/getAllMenu")
    public List<Menu> getAllMenu(){
        //因为有菜单里子菜单 所以不能用plus的封装去查 自己写sql

        return menuService.getAllmenus();
    }



    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @ApiOperation(value = "根据id查询菜单角色")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMidByRid(Integer rid){

        //利用stream()流把对象转成菜单ID 然后转成arraylist。 最难理解的地方 jdk8新特性；
        List<Integer> collect = menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid", rid))
                .stream().map(MenuRole::getMid).collect(Collectors.toList());

        return collect;
    }

    @ApiOperation(value = "更新角色菜单")
    @PostMapping("/update")
    public RestBean updateRole(Integer rid,Integer[] mids) {

        return menuRoleService.updateRole(rid,mids);



    }
}
