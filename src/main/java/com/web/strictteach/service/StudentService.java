package com.web.strictteach.service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.strictteach.exception.selfDefineException;
import com.web.strictteach.mapper.StudentMapper;
import com.web.strictteach.pojo.Student;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {
    @Resource
    private StudentMapper studentMapper;
    public List<Student> selectAll(Student student) {
        return studentMapper.selectAll(student);

    }

    public Student selectById(Integer id) {
        return studentMapper.selectById(id);
    }

    public PageInfo<Student> selectPage(Student student, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Student> list = studentMapper.selectAll(student);
        return PageInfo.of(list);
    }

    public void add(Student student) {
        studentMapper.insert(student);
    }

    public void update(Student student) {
        studentMapper.update(student);
    }

    public void delete(Integer id) {
        studentMapper.delete(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for(Integer id:ids){
            this.delete(id);
        }
    }

}
