package com.example.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//接口测试类
@RestController
public class hellowController {

    @GetMapping("/hello")
    public String hellow(){

        return "测试成功了";
    }
    @GetMapping("/employee/basic/hello1")
    public String hellow1(){
        return "只能查基本资料测试成功";
    }
    @GetMapping("/employee/advanced/hello2")
    public String hellow2(){
        return "不能查高级资料测试成功";
    }

}
