package com.web.strictteach.pojo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SC {
//    private Integer Cid;
    private Integer id;
    private String subject;
    private String name;
    private String grade;
}
