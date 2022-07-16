package com.example.server.controller;

import com.example.server.pojo.Admin;
import com.example.server.service.IAdminService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * < 在线聊天 >
 */
@RestController
@RequestMapping("/chat")
public class LiaotianController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/admin")
    public List<Admin> getAllAdmin(String keywords){

        return adminService.getAllAdmin(keywords);

    }


}
