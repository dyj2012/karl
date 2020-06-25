package com.karl.core.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.karl.core.entity.TestJsonEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author karl
 */
@Mapper
public interface TestJsonMapper extends BaseMapper<TestJsonEntity> {


    /**
     * mysql的批量插入
     *
     * @param list
     * @return
     */
    int mysqlBatchInsert(List<TestJsonEntity> list);

}