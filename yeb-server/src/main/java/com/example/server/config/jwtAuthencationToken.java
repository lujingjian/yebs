package com.example.server.config;

import com.example.server.pojo.RestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class jwtAuthencationToken extends OncePerRequestFilter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenhead;
    @Autowired
    private com.example.server.config.utils.jwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    /*先判断这个toekn存不存在 如果存在 就用userDetailsService去登陆 然后我们进行验证token是否有效 如果有效重新设置到用户对象里面
     * */
    @Override
    protected void doFilterInternal(HttpServletRequest request,//相当于前置拦截
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String tokenheaders = request.getHeader(tokenHeader); //拿到要验证的头部
        //System.out.println(tokenheaders);
        //存在token
        if (null!=tokenheaders && ""!=tokenheaders && tokenheaders.startsWith(tokenhead)){

            String autuToken=tokenheaders.substring(tokenhead.length());
            String username = jwtUtil.getUsernameFromToken(autuToken);
            //token存在但是未登陆
            if (null !=username && null== SecurityContextHolder.getContext().getAuthentication()){
                //登陆它
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //登陆后我们还有判断是否有效TOken 重新设置用户对象 拿令牌去比较
                if (jwtUtil.dataToken(autuToken,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken
                            =new UsernamePasswordAuthenticationToken(userDetails,null,
                            userDetails.getAuthorities());   //   userDetails.getAuthorities()是权限认定
                    //重新设置到用户对象里去
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //最后一步
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        //放行
        filterChain.doFilter(request,response);
    }
    }
