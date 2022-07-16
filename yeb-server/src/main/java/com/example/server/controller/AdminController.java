package com.example.server.controller;


import com.example.server.pojo.Admin;
import com.example.server.pojo.RestBean;
import com.example.server.pojo.Role;
import com.example.server.service.IAdminService;
import com.example.server.service.IRoleService;
import freemarker.core.ReturnInstruction;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IRoleService roleService;

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/")
    public List<Admin> getAllAdmin(String keywords){

        return adminService. getAllAdmin(keywords);
    }
    @ApiOperation(value = "更新操作员")
    @PutMapping("/update")
    public RestBean upDate(@RequestBody Admin admin){
        if (adminService.updateById(admin)){
            return RestBean.success("更新成功");
        }
        return RestBean.error("更新失败");
    }

    @ApiOperation(value = "删除操作员")
    @DeleteMapping("/{id}")
    public RestBean deleteAdmin(Integer id){
        if (adminService.removeById(id)){
            return RestBean.success("删除成功");
        }
        return RestBean.error("删除失败");
    }

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/roles")
    public List<Role> getAllRole(){

        return roleService.list();
    }
    @ApiOperation(value = "更新操作员角色")
    @PutMapping("/upRole")
    public RestBean upRole(Integer adminId,Integer[] ids){

        return adminService.upRole(adminId,ids);

        }
}


