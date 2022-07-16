package com.example.server.controller;


import com.example.server.mapper.JoblevelMapper;
import com.example.server.pojo.Joblevel;
import com.example.server.pojo.RestBean;
import com.example.server.service.impl.JoblevelServiceImpl;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>职称增删改
 *
 * @author zhoubin
 * @since 2021-10-25
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {

    @Autowired
    private JoblevelServiceImpl joblevelService;

    @ApiOperation(value = "查找所有职称")
    @GetMapping("/all")
    public List<Joblevel> getJobLevel() {

        return joblevelService.list();
    }

    @ApiOperation(value = "添加职称")
    @PostMapping("/add")
    public RestBean addJobLevel(@RequestBody Joblevel job) {
        job.setCreateDate(LocalDateTime.now());
        if (joblevelService.save(job)) {
            return RestBean.success("添加职称成功");
        }
        return RestBean.error("添加失败");

    }

    @ApiOperation(value = "更新职位")
    @PutMapping("/update")
    public RestBean updateJobLevel(@RequestBody Joblevel job) {
        job.setCreateDate(LocalDateTime.now());
        if (joblevelService.updateById(job)) {
            return RestBean.success("修改成功");
        }
        return RestBean.error("修改失败");
    }

    @ApiOperation(value = "单个删除职称")
    @DeleteMapping("/{id}")
    public RestBean deleteJobLecel(Integer id){

        if (joblevelService.removeById(id)) {
            return  RestBean.success("成功删除该选项");
        }
        return RestBean.error("删除失败");
    }
    @ApiOperation(value = "多个删除")
    @DeleteMapping("/multipleDelete")
    public RestBean multipleDeleteJob(Integer[] ids){
        if (joblevelService.removeByIds(Arrays.asList(ids))){
            return RestBean.success("所选的职称删除成功");
        }
        return RestBean.success("所选的职称删除失败");
    }

}
