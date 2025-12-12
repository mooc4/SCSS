package com.web.strictteach.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code; // 编码：1成功，0为失败
    private String msg;   // 错误信息
    private Object data;  // 数据

    // 成功（无数据）
    public static Result success() {
        Result result = new Result();
        result.code = 1;
        result.msg = "success";
        return result;
    }

    // 成功（带数据）
    public static Result success(Object object) {
        Result result = new Result();
        result.data = object;
        result.code = 1;
        result.msg = "success";
        return result;
    }

    // 失败（带错误信息）
    public static Result error(String msg) {
        Result result = new Result();
        result.code = 404;
        result.msg = msg;
        return result;
    }
}