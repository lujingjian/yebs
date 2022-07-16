package com.example.server.config.security.conponent;

import com.example.server.pojo.RestBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*访问接口没有权限时候的类*/
@Component
public class RestnotAccessHander implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json");
        PrintWriter out = httpServletResponse.getWriter();
        RestBean rest = RestBean.error("没权限 或者权限不足请联系管理员");
        rest.setCode(403);
        out.write(new ObjectMapper().writeValueAsString(rest));
        out.flush();
        out.close();

    }
}
