package com.example.server.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.server.pojo.Employee;
import com.example.server.pojo.MailConstants;
import com.example.server.pojo.MailLog;
import com.example.server.service.IEmployeeService;
import com.example.server.service.IMailLogService;
import com.sun.org.apache.xpath.internal.operations.Lt;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * < 邮件发送的定时类>
 */
@Component
public class mailTask {

    @Autowired
    private IMailLogService mailLogService;

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask(){

        List<MailLog> list =mailLogService.list(new QueryWrapper<MailLog>()
                .eq("status",0).lt("tryTime", LocalDateTime.now()));
        list.forEach(mailLog -> {
            // 如果重试超过 3次，更新状态为投递失败，不再重试
            if (3<=mailLog.getCount()) {
                mailLogService.update(new UpdateWrapper<MailLog>().set("status",2).eq("msgId",mailLog.getMsgId()));
            }
            mailLogService.update(new UpdateWrapper<MailLog>().set("count",mailLog.getCount()+1).set("updateTime",
                    LocalDateTime.now()).set("tryTime",LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT))
                    .eq("msgId",mailLog.getMsgId()));
            Employee emp = employeeService.getEmployee(mailLog.getEid()).get(0);
            // 重新发送消息,参数：交换机名、路由键、对象、
            rabbitTemplate.convertAndSend("MailConstants.MAIL_EXCHANGE_NAME",MailConstants.MAIL_ROUTING_KEY_NAME,emp,
                    new CorrelationData(mailLog.getMsgId()));
        });

    }


}
