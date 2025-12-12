package com.web.strictteach.service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.strictteach.exception.selfDefineException;
import com.web.strictteach.mapper.CourseMapper;
import com.web.strictteach.pojo.Course;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService {
    @Resource
    private CourseMapper courseMapper;
    public List<Course> selectAll(Course course) {
        return courseMapper.selectAll(course);
    }

    public Course selectById(Integer id) {
        return courseMapper.selectById(id);
    }

    public PageInfo<Course> selectPage(Course course, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Course> list = courseMapper.selectAll(course);
        return PageInfo.of(list);
    }

    public void add(Course course) {
        courseMapper.insert(course);
    }

    public void update(Course course) {
        courseMapper.update(course);
    }

    public void delete(Integer id) {
        courseMapper.delete(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for(Integer id:ids){
            this.delete(id);
        }
    }

}
