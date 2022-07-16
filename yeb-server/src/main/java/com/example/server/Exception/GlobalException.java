package com.example.server.Exception;

import com.example.server.pojo.RestBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

//控制类增强类
@RestControllerAdvice
public class GlobalException {
    //捕获全局sql异常
    @ExceptionHandler(SQLException.class)
    public RestBean mysqlException(SQLException e){
        if (e instanceof SQLIntegrityConstraintViolationException){
            return RestBean.error("它有关联表请先删除它");

        }
        return RestBean.error("数据库方面的异常 操作失败");
    }

}
