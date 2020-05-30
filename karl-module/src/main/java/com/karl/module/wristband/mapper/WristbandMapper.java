package com.karl.module.wristband.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.karl.module.wristband.api.entity.WristbandEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 手环 mapper
 *
 * @author Think
 */
@Mapper
public interface WristbandMapper extends BaseMapper<WristbandEntity> {
}