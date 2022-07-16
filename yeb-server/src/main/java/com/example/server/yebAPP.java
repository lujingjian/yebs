package com.example.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@MapperScan("com.example.server.mapper")
@EnableScheduling
public class yebAPP {
    public static void main(String[] args) {
        SpringApplication.run( yebAPP.class,args);
    }
}
