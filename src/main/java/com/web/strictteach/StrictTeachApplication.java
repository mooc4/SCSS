package com.web.strictteach;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.web.strictteach.mapper")
public class StrictTeachApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrictTeachApplication.class, args);
    }

}
