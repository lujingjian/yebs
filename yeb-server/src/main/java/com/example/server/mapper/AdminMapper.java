package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.Admin;
import com.example.server.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
public interface AdminMapper extends BaseMapper<Admin> {


    ///获取所有的操作员
    List<Admin> getAllAdmin(@Param("id") Integer id, @Param("keywords") String keywords);
}
