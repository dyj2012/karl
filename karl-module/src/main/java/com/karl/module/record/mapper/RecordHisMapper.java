package com.karl.module.record.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.karl.module.record.api.entity.RecordEntity;
import com.karl.module.record.api.entity.RecordHisEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 记录 mapper
 *
 * @author Think
 */
@Mapper
public interface RecordHisMapper extends BaseMapper<RecordHisEntity> {
}