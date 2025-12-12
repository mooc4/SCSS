package com.web.strictteach.service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.strictteach.exception.selfDefineException;
import com.web.strictteach.mapper.SCMapper;
import com.web.strictteach.pojo.SC;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SCService {
    @Resource
    private SCMapper scMapper;
    public List<SC> selectAll(SC sc) {
        return scMapper.selectAll(sc);

    }

    public SC selectById(Integer id) {
        return scMapper.selectById(id);
    }

    public PageInfo<SC> selectPage(SC sc, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<SC> list = scMapper.selectAll(sc);
        return PageInfo.of(list);
    }

    public void add(SC sc) {
        scMapper.insert(sc);
    }

    public void update(SC sc) {
        scMapper.update(sc);
    }

    public void delete(Integer id) {
        scMapper.delete(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for(Integer id:ids){
            this.delete(id);
        }
    }

}
