package com.example.server.controller;


import com.example.server.pojo.Department;
import com.example.server.pojo.RestBean;
import com.example.server.service.IDepartmentService;
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
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Autowired
     private IDepartmentService departmentService;

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/all")
    public List<Department> getAllDepartment(){


        return departmentService.getAllDepartment();
    }

    @ApiOperation(value = "添加部门")
    @PostMapping("/add")
    public RestBean addDepartment(@RequestBody Department dep){

        return departmentService.addDepartment(dep);
    }
    @ApiOperation(value = "删除部门")
    @PostMapping("/{id}")
    public RestBean deleteDepartment(Integer id){

        return departmentService.deleteDepartment(id);
    }

}
