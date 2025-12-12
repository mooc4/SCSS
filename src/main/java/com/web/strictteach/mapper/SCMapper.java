package com.web.strictteach.mapper;

import com.web.strictteach.pojo.SC;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface SCMapper {
    List<SC> selectAll(SC sc);

    @Select("select * from sc where id=#{id}")
    SC selectById(Integer id);

    void insert(SC sc);

    void update(SC sc);

    void delete(Integer id);


}
