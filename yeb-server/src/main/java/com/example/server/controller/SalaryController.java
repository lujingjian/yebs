package com.example.server.controller;


import com.example.server.pojo.RestBean;
import com.example.server.pojo.Salary;
import com.example.server.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
@RequestMapping("/salary/sob")
public class SalaryController {

    @Autowired
    private ISalaryService salaryService;

    @ApiOperation(value = "获取所有工资账套")
    @GetMapping
    public List<Salary> getAllSalary(){

        return salaryService .list();
    }

    @ApiOperation(value = "添加工资账套")
    @PostMapping("/")
    public RestBean addSalary(@RequestBody Salary salary) {
        salary.setCreateDate(LocalDateTime.now());
        if (salaryService.save(salary)) {
            return RestBean.success("添加成功！");
        }
        return RestBean.error("添加失败！");
    }

    @ApiOperation(value = "删除工资账套")
    @DeleteMapping("/{id}")
    public RestBean deleteSalary(@PathVariable Integer id) {//@PathVariable 接收请求路径中占位符的值
        if (salaryService.removeById(id)) {
            return RestBean.success("删除成功！");
        }
        return RestBean.error("删除失败！");
    }
    @ApiOperation(value = "更新工资账套")
    @PutMapping("/")
    public RestBean updateSalary(@RequestBody Salary salary) {
        if (salaryService.updateById(salary)) {
            return RestBean.success("更新成功！");
        }
        return RestBean.error("更新失败！");
    }


}
