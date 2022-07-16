package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.Employee;
import com.example.server.pojo.RestBean;
import com.example.server.pojo.RestPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
public interface IEmployeeService extends IService<Employee> {

    //获取所有员工（分页）
    RestPageBean getEmployeeByPage(Integer currentPage, Integer size,
                                   Employee employee, LocalDate[] beginDateScope);

    //获取员工工号
   String maxWorkID();

    //添加员工
    RestBean addEmp(Employee employee);

    // 导出所有
    List<Employee> getEmployee(Integer id);

    //获取员工账套表(分页) 然后关联工资账套
    RestPageBean getEmployeeWithSalary(Integer currentPage, Integer size);
}
