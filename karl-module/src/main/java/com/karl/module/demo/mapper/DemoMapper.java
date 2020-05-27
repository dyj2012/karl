package com.karl.module.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.karl.module.demo.api.entity.DemoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Think
 */
@Mapper
public interface DemoMapper extends BaseMapper<DemoEntity> {
}