package com.web.strictteach.controller;

import com.github.pagehelper.PageInfo;
import com.web.strictteach.pojo.Employee;
import com.web.strictteach.pojo.Result;
import com.web.strictteach.service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")                 //增加一个路径层级
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;
    @GetMapping("/selectAll")
    public Result selectAll(Employee employee){
        List<Employee> list = employeeService.selectAll(employee);
        return Result.success(list);
    }

    @GetMapping("/selectOne")
    public Result selectById(@RequestParam Integer id , @RequestParam(required = false) String name){
        Employee employee = employeeService.selectById(id);
        return Result.success(employee);
    }

    @GetMapping("/selectPage")
    public Result selectPage(Employee employee,
                             @RequestParam(defaultValue = "1") Integer currentPage,
                             @RequestParam(defaultValue = "3")Integer pageSize){
        PageInfo<Employee> pageInfo = employeeService.selectPage(employee,currentPage, pageSize);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Employee employee){
        employeeService.add(employee);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer id){
        employeeService.delete(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        employeeService.deleteBatch(ids);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Employee employee){
        employeeService.update(employee);
        return Result.success();
    }
}
