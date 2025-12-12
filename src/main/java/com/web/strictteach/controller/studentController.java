package com.web.strictteach.controller;

import com.github.pagehelper.PageInfo;
import com.web.strictteach.pojo.Student;
import com.web.strictteach.pojo.Result;
import com.web.strictteach.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")                 //增加一个路径层级
public class studentController {

    @Resource
    private StudentService studentService;
    @GetMapping("/selectAll")
    public Result selectAll(Student student){
        List<Student> list = studentService.selectAll(student);
        return Result.success(list);
    }

    @GetMapping("/selectOne")
    public Result selectById(@RequestParam Integer id , @RequestParam(required = false) String name){
        Student student = studentService.selectById(id);
        return Result.success(student);
    }

    @GetMapping("/selectPage")
    public Result selectPage(Student student,
                             @RequestParam(defaultValue = "1") Integer currentPage,
                             @RequestParam(defaultValue = "3")Integer pageSize){
        PageInfo<Student> pageInfo = studentService.selectPage(student,currentPage, pageSize);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Student student){
        studentService.add(student);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer id){
        studentService.delete(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        studentService.deleteBatch(ids);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Student student){
        studentService.update(student);
        return Result.success();
    }
}
