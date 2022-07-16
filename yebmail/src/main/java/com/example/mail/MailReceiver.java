package com.example.mail;

import com.example.server.pojo.Employee;
import com.example.server.pojo.MailConstants;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

/**
 * < 接收信息类 >
 */
@Component
public class MailReceiver {

    //打印日志准备
    private static final Logger LOGGER= LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    private JavaMailSender javaMailSender;//邮件发送

    @Autowired
    private MailProperties mailProperties;//邮件配置

    @Autowired
    private TemplateEngine templateEngine;//引擎

    @Autowired
    private RedisTemplate redisTemplate;



    @RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME) // 监听 队列
    public void handler(Message message, Channel channel){

        Employee employee = (Employee) message.getPayload();//拿到员工

        MessageHeaders headers = message.getHeaders();
        long tag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);//消息序号

        //String header = "spring_returned_message_correlation";
        String mesId = (String) headers.get("spring_returned_message_correlation");//得到消息ID

       // String mesId = (String) headers.get(header);

        HashOperations hashOperations = redisTemplate.opsForHash();
        try {

            if (hashOperations.entries("mail_log").containsKey(mesId)){
                LOGGER.error("消息被消费了》》》》》》》",mesId);
                //手动确认消息
                channel.basicAck(tag,false);
                return;

            }
            //创建邮件发送
            MimeMessage Message = javaMailSender.createMimeMessage();
            MimeMessageHelper MessageHelper = new MimeMessageHelper(Message);

            //发件人
            MessageHelper.setFrom(mailProperties.getUsername());
            //收件人
            MessageHelper.setTo(employee.getEmail());
            //主题
            MessageHelper.setSubject("入职欢迎邮件");
            MessageHelper.setSentDate(new Date());//当前时间发送
            //准备邮件内容
            Context context = new Context();//要导模板的包
            context.setVariable("name",employee.getName());//名字
            context.setVariable("posName",employee.getPosition().getName());//职位
            context.setVariable("jobleveName",employee.getJoblevel().getName());//职称
            context.setVariable("departmentName",employee.getDepartment().getName());//部门
            //通过引擎发送
            String mail = templateEngine.process("mail", context);
            //最后发送邮件
            MessageHelper.setText(mail,true);//设置为HTML
            javaMailSender.send(Message);
            LOGGER.info("邮件发送成功");
            //将消息ID存入redis
            hashOperations.put("mail_log",mesId,"成了，完活");
            //手动确认消息
            channel.basicAck(tag,false);

        } catch (MessagingException | IOException e) {
            /**
             * 手动确认消息
             * tag ： 消息序号
             * multiple: 是否确认多条
             * requeue: 是否退回到队列
             */
            try {
                channel.basicNack(tag,false,true);
            } catch (IOException ioException) {
                LOGGER.error("邮件发送失败 ===========> {}", e.getMessage());
            }
            LOGGER.error("邮件发送失败 ===========> {}", e.getMessage());
        }


    }



}
