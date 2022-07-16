package com.example.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.MenuRoleMapper;
import com.example.server.pojo.MenuRole;
import com.example.server.pojo.RestBean;
import com.example.server.service.IMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {


    @Autowired
    private  MenuRoleMapper menuRoleMapper;

    //先删除角色下所有菜单核心
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RestBean updateRole(Integer rid, Integer[] mids) {
        //
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid",rid));
        if (null==mids || 0==mids.length){
            return RestBean.error("更新成功");
        }
        //批量更新
        Integer result=menuRoleMapper.insertRecord(rid,mids);
        if (result==mids.length){
            return  RestBean.success("更新成功");
        }

        return RestBean.error("更新失败");
    }

    //更新角色菜单
   /* @Override
    public RestBean updateRole(Integer rid, Integer[] mids) {

        return null;
    }*/

    /*@Autowired
    private  MenuRoleMapper menuRoleMapper;

    @Override
    @Transactional
    public RestBean updateRole(Integer rid, Integer[] mids) {

        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid",rid));
        if (null==mids || 0==mids.length){
            return RestBean.error("更新成功");
        }
        //批量更新
        menuRoleMapper.insertRecord(rid,mids);

        return null;
    }*/
}
