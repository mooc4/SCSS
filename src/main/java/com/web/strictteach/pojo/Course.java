package com.web.strictteach.pojo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Course {
    private Integer id;
    private String teacher;
    private String sex;
    private Integer age;
    private String subject;
}
