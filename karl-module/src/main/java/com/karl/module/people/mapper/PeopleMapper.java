package com.karl.module.people.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.karl.module.entity.PeopleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * demo mapper
 *
 * @author Think
 */
@Mapper
public interface PeopleMapper extends BaseMapper<PeopleEntity> {
}