package com.web.strictteach.pojo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Student {
    private Integer id;
    private String sex;
    private Integer age;
    private String name;
}
