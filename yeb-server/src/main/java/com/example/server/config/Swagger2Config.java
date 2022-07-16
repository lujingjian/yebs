package com.example.server.config;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/*Swagger2配置类*/
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Value("${jwt.tokenHeader}")
    private String head;
    @Bean
    public Docket createRestApi(){

        //文档类型是丝袜哥2
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()//扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.example.server.controller"))
                .paths(PathSelectors.any())
                .build()
                    //添加登录认证
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());


    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("夜观天象 狗托附体 报错是不可能的... ")
                .description("就是个办公的接口文档")
                .contact(new Contact("陆景坚","http:localhost:8081","2356690795@qq.com"))
                .version("1.0")
                .build();

    }

    private List<ApiKey> securitySchemes(){
        //设置请求头信息
        List<ApiKey> list =new ArrayList<>();
       ApiKey apiKey=new ApiKey("Authorization","Authorization","Header");
       list.add(apiKey);
       return list;
    }
    private List<SecurityContext> securityContexts(){
        //1.0设置需要登录认证的路径
        List<SecurityContext> result=new ArrayList<>();
        result.add(getContextByPath("/hello/.*"));
        return result;
    }
        /*1.1*/
    private SecurityContext getContextByPath(String s) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(s))
                .build();
    }
    /*1.2*/
    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result=new ArrayList<>();
        AuthorizationScope authorizationScope=new AuthorizationScope("global",/*描述范围*/
                "accessEverything"/*所有请求*/);
       AuthorizationScope[] au=new AuthorizationScope[1];//准备一个数组
       au[0] =authorizationScope;
       //必须得用数组才能传进去 没办法
       result.add(new SecurityReference("Authorization",au));
       return result;
    }


}
