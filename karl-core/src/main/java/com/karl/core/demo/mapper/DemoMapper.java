package com.karl.core.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.karl.core.demo.api.entity.DemoEntity;
import com.karl.core.user.api.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Think
 */
@Mapper
public interface DemoMapper extends BaseMapper<DemoEntity> {
}