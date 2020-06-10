package com.karl.core.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.karl.core.entity.OperationLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志 mapper
 *
 * @author Think
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLogEntity> {
}