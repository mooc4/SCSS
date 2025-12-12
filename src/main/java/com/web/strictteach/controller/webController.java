package com.web.strictteach.controller;

import com.web.strictteach.exception.selfDefineException;
import com.web.strictteach.pojo.Employee;
import com.web.strictteach.pojo.Result;
import com.web.strictteach.service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

//    @GetMapping("/hello")
//    public Result hello(){
//        return  Result.success("hello world");
//    }
//    @GetMapping("/weather")
//    public Result weather(){
//        return Result.success("天气 🌤");
//    }
//
//    @GetMapping("/count")
//    public Result count(){
//        throw new selfDefineException("错误，禁止访问");
//        //return Result.success(a);
//    }
//    @GetMapping("/map")
//    public Result map(){
//        Map<String,Integer> map = new HashMap<>();
//        map.put("count",10);
//        map.put("count2",20);
//        return Result.success(map);
//    }

@RestController
public class webController {
@Resource
public EmployeeService employeeService;
@PostMapping("/login")
public Result login(@RequestBody Employee employee){
    Employee dpEmployee = employeeService.login(employee);
    return Result.success(dpEmployee);
}
    @PostMapping("/regist")
    public Result regist(@RequestBody Employee employee){
        employeeService.regist(employee);
        return Result.success();
    }

}


