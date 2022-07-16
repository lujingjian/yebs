package com.example.server.config.security.conponent;

import com.example.server.pojo.RestBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*D当登陆或者TOKEN失效时访问接口  自定义返回结果集*/
@Component
public class RestAuthorizePoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest Request,
                         HttpServletResponse Response,
                         AuthenticationException e) throws IOException, ServletException {
        Response.setCharacterEncoding("utf-8");
        Response.setContentType("application/json");
        PrintWriter writer = Response.getWriter();
        RestBean rest = RestBean.error("TOKEN失效未登陆请登录");
        rest.setCode(401);
        writer.write(new ObjectMapper().writeValueAsString(rest));
        writer.flush();
        writer.close();


    }
}
