package com.example.server.pojo;

import com.sun.tracing.dtrace.ArgsAttributes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//公共返回类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestBean {
    //状态码
    private long code;
    //提示信息
    private String message;
    //有时候需要返回的对象
    private Object object;
/*成功返回结果*/
    public static RestBean success(String message){
        return new RestBean(200, message, null);
    }
    public static RestBean success(String message,Object object){
        return new RestBean(200, message, object);
    }

    /*失败返回*/
    public static RestBean error(String message){
        return new RestBean(500, message, null);
    }
    public static RestBean error(String message,Object object){
        return new RestBean(500, message, object);
    }

}
