package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.pojo.Employee;
import com.example.server.pojo.RestBean;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    //获取所有员工（分页）
    Page<Employee> getEmployeeByPage( Page<Employee> page, @Param("employee") Employee employee, @Param("beginDateScope") LocalDate[] beginDateScope);

  //查询工号
  String maxWorkID();

    //查询员工
    List<Employee> getEmployee(Integer id);

    //获取员工账套表(分页) 然后关联工资账套
    IPage<Employee> getEmployeeWithSalary(Page<Employee> page);
}
