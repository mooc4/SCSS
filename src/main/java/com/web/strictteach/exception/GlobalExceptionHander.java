package com.web.strictteach.exception;

import com.web.strictteach.pojo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice("com.web.strictteach.controller")
public class GlobalExceptionHander {

    @ExceptionHandler(selfDefineException.class)
    @ResponseBody  //返回json数据
    public Result error(selfDefineException e){
        e.printStackTrace();
        return Result.error(e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody  //返回json数据
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error("系统错误");
    }


}
