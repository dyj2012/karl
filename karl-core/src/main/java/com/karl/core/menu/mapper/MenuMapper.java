package com.karl.core.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.karl.core.menu.api.entity.MenuEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单 mapper
 *
 * @author Think
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuEntity> {
}