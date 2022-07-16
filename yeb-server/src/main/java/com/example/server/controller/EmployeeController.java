package com.example.server.controller;



import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.example.server.pojo.*;
import com.example.server.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhoubin
 * @since 2021-10-25
 */
@RestController
@RequestMapping("/personnel/emp") // 路径跟菜单表的 url 字段要一样
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;//员工的

    @Autowired
    private IPoliticsStatusService politicsStatusService;//政治面貌的

    @Autowired
    private IJoblevelService joblevelService;//职位的

    @Autowired
    private INationService nationService;//民族的
    @Autowired
    private IPositionService positionService;//职称的

    @Autowired
    private IDepartmentService departmentService;//部门的

    @ApiOperation(value = "获取所有员工（分页）")
    @GetMapping("/")
    public RestPageBean getEmployee(@RequestParam(defaultValue = "1") Integer currentPage,//设置默认参数 当前页1页 每页10条
                                    @RequestParam(defaultValue = "10") Integer size,
                                    Employee employee,
                                    LocalDate[] beginDateScope) {//因为时间没办法接收所以用数组
        return employeeService.getEmployeeByPage(currentPage, size, employee, beginDateScope);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @ApiOperation(value = "获取所有政治面貌")
    @GetMapping("/politicsStatus")
    public List<PoliticsStatus> getAllPoliticsStatus() {
        return politicsStatusService.list();
    }

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/joblevels")
    public List<Joblevel> getAllJoblevels() {
        return joblevelService.list();
    }

    @ApiOperation(value = "获取所有民族")
    @GetMapping("/nations")
    public List<Nation> getAllNations() {
        return nationService.list();
    }

    @ApiOperation(value = "获取所有职位")
    @GetMapping("/Positions")
    public List<Position> getAllPositions() {
        return positionService.list();
    }

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/deps")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartment();
    }

    @ApiOperation(value = "获取工号")
    @GetMapping("/maxWorkID")
    public String maxWorkID() {

        return employeeService.maxWorkID();

    }


    @ApiOperation(value = "添加员工 添加了邮件记得打开Rabbitmq")
    @PostMapping("/")
    public RestBean addEmp(@RequestBody Employee employee) {
        return employeeService.addEmp(employee);
    }

    @ApiOperation(value = "更新员工")
    @PutMapping("/")
    public RestBean updateEmp(@RequestBody Employee employee) {
        if (employeeService.updateById(employee)) {
            return RestBean.success("更新成功！");
        }
        return RestBean.error("更新失败！");
    }


    @ApiOperation(value = "删除员工 不考虑合同期限的")
    @DeleteMapping("/")
    public RestBean deleteEmployee(Integer id){

        if (employeeService.removeById(id)) {

            return RestBean.success("删除成功");
        }

        return RestBean.error("删除失败");
    }

    @ApiOperation(value = "导出员工数据")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response) {
        List<Employee> list = employeeService.getEmployee(null);// 导出所有
        // HSSF 03版，兼容性好一点； 还有一个 07 版的
        ExportParams params = new ExportParams("员工表", "员工表", ExcelType.HSSF);
        // 导出工具类
        Workbook workbook = ExcelExportUtil.exportExcel(params, Employee.class, list);
        ServletOutputStream out = null;
        try {
            // 流形式导出
            response.setHeader("content-type", "application/octet-stream");
            // 防止中文乱码
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("员工表.xls", "UTF-8"));
            out = response.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @ApiOperation(value = "导入员工数据")
    @PostMapping("/import")
    public RestBean importEmployee(MultipartFile file) {
        ImportParams params = new ImportParams();
        // 去掉标题行
        params.setTitleRows(1);
        //各种查所有
        List<Nation> nationList = nationService.list();
        List<PoliticsStatus> politicsStatusesList = politicsStatusService.list();
        List<Department> departmentsList = departmentService.list();
        List<Joblevel> joblevelsList = joblevelService.list();
        List<Position> positionsList = positionService.list();
        try {
            List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, params);

            //循环它
            list.forEach(employee -> {

                // indexOf 在字符串中寻找参数字符串第一次出现的位置并返回该位置。
                Nation nation = new Nation(employee.getNation().getName());//得到对象 对象里有name没有ID
                int i = nationList.indexOf(nation);//得到一个下标位置
                //根据这个下标获取完整对象 这时候就有id了
                Nation nation1 = nationList.get(i);
                //利用nation1完整对象获取id
                Integer needId = nation1.getId();
                //把ID放进去
                employee.setNationId(needId);
                //上面的思路 就是获取 Nation 的 name, 通过 name 获取对应的下标，通过下标获取完整的对象，通过对象获取 id,

                // 政治 id
               employee.setPoliticId(politicsStatusesList.get(politicsStatusesList
                       .indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
                // 部门 id
                employee.setDepartmentId(departmentsList.get(departmentsList
                        .indexOf(new Department(employee.getDepartment().getName()))).getId());
                // 职称 id
                employee.setJobLevelId(joblevelsList.get(joblevelsList
                        .indexOf(new Joblevel(employee.getJoblevel().getName()))).getId());
                // 职位 id
                employee.setPosId(positionsList.get(positionsList
                        .indexOf(new Position(employee.getPosition().getName()))).getId());

            });
            if (employeeService.saveBatch(list)) {
                return RestBean.success("导入成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestBean.error("导入失败！");


    }


}