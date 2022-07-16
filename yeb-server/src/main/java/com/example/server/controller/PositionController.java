package com.example.server.controller;


import com.example.server.pojo.Position;
import com.example.server.pojo.RestBean;
import com.example.server.service.IPositionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {
    //正常的增删改
    @Autowired
    private IPositionService positionService;

    //获取所有职位信息
    @ApiOperation(value = "查询职位")
    @GetMapping("/all")
    public List<Position> getAllPosition() {
        //mybitis-plus 单表的查好处甚至不需要去写mapper 和sql 都封装好了 只需要在控制层写
        return positionService.list();
    }
    //添加职位
    @ApiOperation(value = "添加职位")
    @PostMapping("/add")
    public RestBean addPosition(@RequestBody Position position) {
        //设置添加的时间
        position.setCreateDate(LocalDateTime.now());
        //判断是否添加成功
        if (positionService.save(position)) {

            return RestBean.success("老铁添加成功了");
        }
        return RestBean.error("老表添加失败了");
    }

    //更新职位
    @ApiOperation(value = "更新职位")
    @PutMapping("/update")
    public RestBean updatePosition(@RequestBody Position position) {
        if (positionService.updateById(position)) {
            return RestBean.success("修改成功");
        }
        return RestBean.error("修改失败");
    }

    @ApiOperation(value = "单个删除")
    @DeleteMapping("/delete")
    public RestBean deletePosition(int id) {
        if (positionService.removeById(id)) {
            return RestBean.success("已经删除该选项");
        }
        return RestBean.error("没有删除成功");
    }

    @ApiOperation(value = "多个删除")
    @DeleteMapping("/multipleDelete")
    public RestBean deleteMorePosition(Integer[] integers ) {
        if (positionService.removeByIds(Arrays.asList(integers))) {
            return RestBean.success("已经删除多个选项");
        }
        return RestBean.error("没有删除成功");
    }

}
