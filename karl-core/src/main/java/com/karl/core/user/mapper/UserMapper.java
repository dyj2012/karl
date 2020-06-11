package com.karl.core.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.karl.base.mybatis.MybatisRedisCache;
import com.karl.core.entity.UserEntity;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Think
 */
@Mapper
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
public interface UserMapper extends BaseMapper<UserEntity> {
}