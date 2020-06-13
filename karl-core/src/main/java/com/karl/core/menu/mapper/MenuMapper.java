package com.karl.core.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.karl.base.mybatis.MybatisRedisCache;
import com.karl.core.entity.MenuEntity;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单 mapper
 *
 * @author karl
 */
@Mapper
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
public interface MenuMapper extends BaseMapper<MenuEntity> {
}