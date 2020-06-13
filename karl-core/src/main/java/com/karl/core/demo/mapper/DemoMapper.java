package com.karl.core.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.karl.core.demo.entity.DemoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * demo mapper
 *
 * @author karl
 */
@Mapper
public interface DemoMapper extends BaseMapper<DemoEntity> {
}