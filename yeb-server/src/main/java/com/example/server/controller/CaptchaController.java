package com.example.server.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class CaptchaController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;//注入配置类里的生成图片方法

    @ApiOperation(value = "验证码")
    @GetMapping(value = "/captcha",produces = "image/jpeg")//为了让接口文档能看见图片加produces
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        //通过流传图片 固定写法直接CP
        // 定义response输出类型为image/jpeg类型
        response.setDateHeader("Expires", 0);
        // 设置 standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, mustrevalidate");
        // 设置 IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // 设置standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg的返回类型
        response.setContentType("image/jpeg");

        //-------------------生成验证码 begin --------------------------

        //1.创建文本
        String text = defaultKaptcha.createText();
        System.out.println("验证码内容："+text);
        //2.将验证码放 session
        request.getSession().setAttribute("captcha",text);
            //3.根据文本验证码内容 创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);

        //4.输出流输出图片
        ServletOutputStream outputStream=null;
        try {
             outputStream=response.getOutputStream();
             //有流后就可以用imageio类的write方法 设置格式为jpg
            ImageIO.write(image,"jpg",outputStream);
            outputStream.flush();//刷新
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //判断下空
            if (null!=outputStream ) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        //记得security放行

        //-------------------生成验证码 begin --------------------------
    }
}

