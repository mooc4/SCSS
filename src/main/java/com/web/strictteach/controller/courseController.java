package com.web.strictteach.controller;
import com.github.pagehelper.PageInfo;
import com.web.strictteach.pojo.Course;
import com.web.strictteach.pojo.Result;
import com.web.strictteach.service.CourseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")                 //增加一个路径层级
public class courseController {

    @Resource
    private CourseService courseService;
    @GetMapping("/selectAll")
    public Result selectAll(Course course){
        List<Course> list = courseService.selectAll(course);
        return Result.success(list);
    }

    @GetMapping("/selectOne")
    public Result selectById(@RequestParam Integer id , @RequestParam(required = false) String name){
        Course course = courseService.selectById(id);
        return Result.success(course);
    }

    @GetMapping("/selectPage")
    public Result selectPage(Course course,
                             @RequestParam(defaultValue = "1") Integer currentPage,
                             @RequestParam(defaultValue = "3")Integer pageSize){
        PageInfo<Course> pageInfo = courseService.selectPage(course,currentPage, pageSize);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Course course){
        courseService.add(course);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer id){
        courseService.delete(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        courseService.deleteBatch(ids);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Course course){
        courseService.update(course);
        return Result.success();
    }
}
