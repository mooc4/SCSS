package com.web.strictteach.service;


import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.strictteach.exception.selfDefineException;
import com.web.strictteach.mapper.EmployeeMapper;
import com.web.strictteach.pojo.Employee;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {
    @Resource
    private EmployeeMapper employeeMapper;
    public List<Employee> selectAll(Employee employee) {
        return employeeMapper.selectAll(employee);
    }

    public Employee selectById(Integer id) {
        return employeeMapper.selectById(id);
    }

    public PageInfo<Employee> selectPage(Employee employee, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Employee> list = employeeMapper.selectAll(employee);
        return PageInfo.of(list);
    }

    public void add(Employee employee) {
        String account = employee.getAccount();
        Employee dbEmployee =  employeeMapper.selectByAccount(account);
        if(dbEmployee != null){
            throw new selfDefineException("此账号已存在");
        }
        if(StrUtil.isBlank(employee.getPassword())){
            employee.setPassword("123");                        //默认密码123
        }

        employeeMapper.insert(employee);
    }

    public void update(Employee employee) {
        employeeMapper.update(employee);
    }

    public void delete(Integer id) {
        employeeMapper.delete(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for(Integer id:ids){
            this.delete(id);
        }
    }

    public Employee login(Employee employee) {
        String account = employee.getAccount();
        Employee dbEmployee =  employeeMapper.selectByAccount(account);
        if(dbEmployee == null){
            throw new selfDefineException("没有这个账号");
        }
        String password = employee.getPassword();
        if(!dbEmployee.getPassword().equals(password)){
            throw new selfDefineException("账号或密码错误");
        }

        return dbEmployee;
    }

    public void regist(Employee employee) {
        this.add(employee);
    }
}
