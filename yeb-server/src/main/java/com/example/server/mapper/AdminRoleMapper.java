package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.AdminRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    //获取更新操作员角色
    Integer upRole(Integer adminId, Integer[] ids);
}
