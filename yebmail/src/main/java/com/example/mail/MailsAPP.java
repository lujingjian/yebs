package com.example.mail;

import com.example.server.pojo.MailConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MailsAPP {
    public static void main(String[] args) {
        SpringApplication.run( MailsAPP.class,args);
    }

    @Bean
    public Queue queue(){
        return new  Queue(MailConstants.MAIL_QUEUE_NAME);
    }//修改成队列
}
