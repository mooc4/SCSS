package com.web.strictteach.mapper;

import com.web.strictteach.pojo.Student;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface StudentMapper {
    List<Student> selectAll(Student student);

    @Select("select * from student where id=#{id}")
    Student selectById(Integer id);

    void insert(Student student);

    void update(Student student);

    void delete(Integer id);


}
