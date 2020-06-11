package com.karl.core.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.karl.base.mybatis.MybatisRedisCache;
import com.karl.core.entity.RoleEntity;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * role mapper
 *
 * @author Think
 */
@Mapper
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
public interface RoleMapper extends BaseMapper<RoleEntity> {
}