package com.example.server.controller;

import com.example.server.pojo.Admin;
import com.example.server.pojo.ChatMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <  >
 */
@Controller
public class WsController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    public void handlerMsg(Authentication authentication, ChatMsg msg){
        Admin admin = (Admin) authentication.getPrincipal();//获取当前对象

        msg.setFrom(admin.getUsername());
        msg.setFrom(admin.getName());
        msg.setDate(LocalDateTime.now());
        simpMessagingTemplate.convertAndSendToUser(msg.getTo(),"/queue/chat",msg);//queue 配置里有写




    }


}
