package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.Department;
import com.example.server.pojo.RestBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
public interface IDepartmentService extends IService<Department> {

    //获取所有的部门
    List<Department> getAllDepartment();

    //添加部门
    RestBean addDepartment(Department dep);

    //删除部门
    RestBean deleteDepartment(Integer id);
}
