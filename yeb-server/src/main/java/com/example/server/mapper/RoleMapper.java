package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
public interface RoleMapper extends BaseMapper<Role> {

    //根据用户ID查询角色列表
    List<Role> getRole(Integer adminId);
}
