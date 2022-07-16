package com.example.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.config.utils.adminUtil;
import com.example.server.mapper.MenuMapper;
import com.example.server.pojo.Menu;
import com.example.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    private MenuMapper menuMapper ;
    @Autowired
    private RedisTemplate redisTemplate;//注入redis

    @Override
    public List<Menu>  getMenuByAdminId() {
        //利用Securitity 的全局对象去获取ID
        Integer adminids = adminUtil.getLoginAdmin().getId();
        //System.out.println(adminids);
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        //设置key 让它从redis拿
        List<Menu> listMenus=((List<Menu>) valueOperations.get("menu_" + adminids));
        //判断是否为空
        if (CollectionUtils.isEmpty(listMenus)){
            //通过数据库查
            listMenus =menuMapper.getMenuByAdminId(adminids);
            //把数据放到redis中
            valueOperations.set("menu_"+adminids,listMenus);
        }
        return listMenus;

    }

    @Override
    public List<Menu> getMenusWithRole() {

        /**
         * 根据角色获取菜单列表
         */
        return menuMapper.getMenusWithRole();
    }




    //查询所有菜单
    @Override
    public List<Menu> getAllmenus() {

        return menuMapper. getAllmenus();
    }


}
