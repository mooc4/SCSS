package com.web.strictteach.controller;

import com.github.pagehelper.PageInfo;
import com.web.strictteach.pojo.SC;
import com.web.strictteach.pojo.Result;
import com.web.strictteach.service.SCService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sc")                 //增加一个路径层级
public class scController {

    @Resource
    private SCService scService;
    @GetMapping("/selectAll")
    public Result selectAll(SC sc){
        List<SC> list = scService.selectAll(sc);
        return Result.success(list);
    }

    @GetMapping("/selectOne")
    public Result selectById(@RequestParam Integer id , @RequestParam(required = false) String name){
        SC sc = scService.selectById(id);
        return Result.success(sc);
    }

    @GetMapping("/selectPage")
    public Result selectPage(SC sc,
                             @RequestParam(defaultValue = "1") Integer currentPage,
                             @RequestParam(defaultValue = "3")Integer pageSize){
        PageInfo<SC> pageInfo = scService.selectPage(sc,currentPage, pageSize);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody SC sc){
        scService.add(sc);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer id){
        scService.delete(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        scService.deleteBatch(ids);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody SC sc){
        scService.update(sc);
        return Result.success();
    }
}
