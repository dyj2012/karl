package com.karl.core.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.karl.core.entity.TestEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author karl
 */
@Mapper
public interface TestMapper extends BaseMapper<TestEntity> {


    /**
     * mysql的批量插入
     *
     * @param list
     * @return
     */
    int mysqlBatchInsert(List<TestEntity> list);
}