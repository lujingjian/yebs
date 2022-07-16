package com.example.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.config.utils.adminUtil;
import com.example.server.config.utils.jwtUtil;
import com.example.server.mapper.AdminMapper;
import com.example.server.mapper.AdminRoleMapper;
import com.example.server.mapper.RoleMapper;
import com.example.server.pojo.*;
import com.example.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;//注入mapper
    @Autowired
    private UserDetailsService userDetailsService;//注入框架里的登录方法
    @Autowired
    private PasswordEncoder passwordEncoder;//注入解密方法 因为数据库存的是加密密码 还没有设置哈
    @Autowired
    private jwtUtil jwtUtils;

    @Value("${jwt.tokenHeader}")
    private String tokenheader;

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;




    @Override
    public RestBean login(String username, String password, String code, HttpServletRequest request) {
        //之前把验证码放入session拿出来
        String captcha=(String)request.getSession().getAttribute("captcha");//直接强转
        //判断如果验证码是空的 用户没输入 或者验证码匹配不上
        if (StringUtils.isEmpty(code)||!captcha.equalsIgnoreCase(code)) {//忽略大小写

            return RestBean.error("验证码错误请大佬重新输入");

        }

        /*登录*/
        UserDetails userDetail=userDetailsService.loadUserByUsername(username);//安全框架的的登录方法****
        if (null==userDetail||!passwordEncoder.matches(password,userDetail.getPassword())){
            return RestBean.error("不正确的密码的");
        }
        if (!userDetail.isEnabled()){
            return RestBean.error("账户被禁用请联系客服");
        }
        /*更新安全框架security登录用户对象*/
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                new UsernamePasswordAuthenticationToken(userDetail,null,userDetail.getAuthorities());
        //放在sevurity全局里面更新 主要是为了通过Principal类去获取到我们的用户名然后根据用户名查询用户返回回去
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        //程序执行到这说明成功可以获得JWT令牌
        String token=jwtUtils.getToke(userDetail);
        Map<String,String> tokenMap=new HashMap();
        tokenMap.put("token",token);
        System.out.println(token);
        tokenMap.put("tokenheader",tokenheader);
        System.out.println(tokenheader);
        return RestBean.success("登录成功",tokenMap);
    }





        /*根据用户名获取用户*/
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username)
                .eq("enabled",true));
    }

    //根据用户ID查询角色列表
    @Override
    public List<Role> getRole(Integer adminId) {

        return roleMapper.getRole(adminId);
    }

    //获取所有操作员
    @Override
    public List<Admin> getAllAdmin(String keywords) {

        //注意不能查当前登录的所以还要传当前登录的用户
        return adminMapper.getAllAdmin(adminUtil.getLoginAdmin().getId(),keywords);
    }

    //获取更新操作员角色
    @Override
    @Transactional
    public RestBean upRole(Integer adminId, Integer[] ids) {

        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId",adminId));

        Integer result = adminRoleMapper.upRole(adminId, ids);
        if (ids.length==result){
            return RestBean.success("更新成功");
        }
        return RestBean.error("更新失败");
    }



}
