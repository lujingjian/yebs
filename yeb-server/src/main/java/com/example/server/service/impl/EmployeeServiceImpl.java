package com.example.server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.EmployeeMapper;
import com.example.server.mapper.MailLogMapper;
import com.example.server.pojo.*;
import com.example.server.service.IEmployeeService;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private  EmployeeMapper employeeMapper;

    @Autowired
    private  IEmployeeService iEmployeeService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailLogMapper mailLogMapper;


    @Override
    public RestPageBean getEmployeeByPage(Integer currentPage, Integer size,
                                          Employee employee, LocalDate[] beginDateScope) {

        //开启分页
        Page<Employee> page = new Page<>(currentPage,size);//传当前页和每页的大小

        Page<Employee> employeeByPage = baseMapper.getEmployeeByPage(page, employee, beginDateScope);

        RestPageBean restPageBean=new RestPageBean(employeeByPage.getTotal(), employeeByPage.getRecords());

        return restPageBean;

    }

    //获取工号
    @Override
    public String maxWorkID() {

        //利用stringbuffer字符串的追加
        String workID = employeeMapper.maxWorkID();

        String s = String.valueOf(Integer.parseInt(workID) + 1);

        StringBuilder newWorkID = new StringBuilder(s);
        //如果查询长度不等于 数据库设计的长度添加0
        while (newWorkID.length() < workID.length()) {
            newWorkID.insert(0, "0");
        }

        return newWorkID.toString();
    }

    //添加员工
    @Override
    public RestBean addEmp(Employee employee) {

        // 处理合同期限，保留两位小数
        LocalDate beginContract = employee.getBeginContract();// 得到合同开始时间

        LocalDate endContract = employee.getEndContract();// 合同结束时间
        // 计算 两个日期相差多少天
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        // 保留两位小数
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        // 计算以年为单位
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days / 365.00)));

        if (1 == baseMapper.insert(employee)) {
            // 获取当前行添加员工记录
            Employee emp = baseMapper.getEmployee(employee.getId()).get(0);

            //创建随机的ID
            String msgId = UUID.randomUUID().toString();
            // 数据库记录发送的消息 消息落库
           // String msgId = "123456";//测试的...
            MailLog mailLog = new MailLog();//把各种常量放进去
            mailLog.setMsgId(msgId);//消息ID
            mailLog.setEid(employee.getId());//员工id
            mailLog.setStatus(0);//状态
            mailLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);//路由键值
            mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);//交换机
            mailLog.setCount(0);//重置次数
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));//重置时间
            mailLog.setCreateTime(LocalDateTime.now());//创建时间
            mailLog.setUpdateTime(LocalDateTime.now());//更新时间
            mailLogMapper.insert(mailLog); // 把设置的数据插入数据表




            // 发送信息
//            rabbitTemplate.convertAndSend(
//                    "MailConstants.MAIL_EXCHANGE_NAME",
//                    MailConstants.MAIL_ROUTING_KEY_NAME,
//                    emp,
//                    new CorrelationData(msgId));
            //mq路由的key再放置ID
            rabbitTemplate.convertAndSend(
                    MailConstants.MAIL_EXCHANGE_NAME,
                    MailConstants.MAIL_ROUTING_KEY_NAME,
                    emp,
                    new CorrelationData(msgId));

            return RestBean.success("添加成功！");
        }
        return RestBean.error("添加失败！");
    }

    //查询员工
    @Override
    public List<Employee> getEmployee(Integer id) {

        return  employeeMapper.getEmployee(id);

    }

    //获取员工账套表(分页) 然后关联工资账套
    @Override
    public RestPageBean getEmployeeWithSalary(Integer currentPage, Integer size) {

        //开启分页
        Page<Employee> page = new Page<>(currentPage,size);
        IPage<Employee> salary =employeeMapper.getEmployeeWithSalary(page);

        RestPageBean restPageBean=new RestPageBean(salary.getTotal(),salary.getRecords());

        return restPageBean;
    }


}
