package com.karl.core.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.karl.core.entity.TestZipEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author karl
 */
@Mapper
public interface TestZipMapper extends BaseMapper<TestZipEntity> {


    /**
     * mysql的批量插入
     *
     * @param list
     * @return
     */
    int mysqlBatchInsert(List<TestZipEntity> list);

}