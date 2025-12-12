package com.web.strictteach.mapper;

import com.web.strictteach.pojo.Course;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface CourseMapper {
    List<Course> selectAll(Course course);

    @Select("select * from student where id=#{id}")
    Course selectById(Integer id);

    void insert(Course course);

    void update(Course course);

    void delete(Integer id);


}
