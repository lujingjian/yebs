package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.Department;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    //获取所有的部门
    List<Department> getAllDepartment(Integer parentId);

        //添加部门
    void addDepartment(Department dep);
        //删除部门
    void deleteDepartment(Department department);
}
