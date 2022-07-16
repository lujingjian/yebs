package com.example.server.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.server.pojo.Employee;
import com.example.server.pojo.RestBean;
import com.example.server.pojo.RestPageBean;
import com.example.server.pojo.Salary;
import com.example.server.service.IEmployeeService;
import com.example.server.service.ISalaryService;
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
@RequestMapping("/salary/sobcfg")
public class SalarySobController {

    @Autowired
    private ISalaryService salaryService;

    @Autowired
    private IEmployeeService employeeService;

    //获取所有工资账套
    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("/salaries")
    public List<Salary> getAllSalary(){

        return salaryService .list();
    }

    //获取员工账套表(分页) 然后关联工资账套

    @ApiOperation(value = "获取所有员工账套(分页）")
    @GetMapping("/")
    public RestPageBean getEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer currentPage,
                                              @RequestParam(defaultValue = "10") Integer size) {
        return employeeService.getEmployeeWithSalary(currentPage, size);
    }

    //更新员工账套
    @ApiOperation(value = "更新员工账套")
    @PutMapping("/")
    public RestBean getEmployeeMoney(Integer eid,Integer sid ){
        if (employeeService.update(new UpdateWrapper<Employee>().set("salaryId",sid).eq("id",eid))) {
            return RestBean.success("更新成功");
        }
        return RestBean.error("更新失败");
    }

}
