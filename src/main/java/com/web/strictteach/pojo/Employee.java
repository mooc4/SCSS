package com.web.strictteach.pojo;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Employee {
    private Integer id;
    private String name;
    private String sex;
    private Integer age;
    private String password;
    private String account;
}
