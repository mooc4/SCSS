package com.web.strictteach.mapper;

import com.web.strictteach.pojo.Employee;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EmployeeMapper {
    List<Employee> selectAll(Employee employee);

    @Select("select * from employee where id=#{id}")
    Employee selectById(Integer id);

    void insert(Employee employee);

    void update(Employee employee);

    void delete(Integer id);

    @Select("select * from employee where account=#{account}")
    Employee selectByAccount(String account);
}
