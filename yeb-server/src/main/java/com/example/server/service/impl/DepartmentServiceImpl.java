package com.example.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.DepartmentMapper;
import com.example.server.pojo.Department;
import com.example.server.pojo.RestBean;
import com.example.server.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    //获取所有的部门
    @Override
    public List<Department> getAllDepartment() {

        return departmentMapper.getAllDepartment(-1);
    }

    //添加部门
    @Override
    public RestBean addDepartment(Department dep) {
        //直接去调用存储过程 先把它设置为TRUE
        dep.setEnabled(true);
        departmentMapper.addDepartment(dep);
        if (1==dep.getResult()){
            return RestBean.success("成功添加");
        }
        return RestBean.error("成功失败");

    }

    @Override
    public RestBean deleteDepartment(Integer id) {
        Department department=new Department();
        department.setId(id);

        departmentMapper. deleteDepartment(department);
        if (-2==department.getResult()){
            return RestBean.error("添加不成功该部门下还有子部门");
        }
        if (-1==department.getResult()){
            return RestBean.error("添加不成功该部门下还有员工");
        }
        if (1==department.getResult()){
            return RestBean.success("删除成功");
        }

        return RestBean.error("删除失败") ;
    }
}
