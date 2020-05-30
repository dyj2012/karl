package com.karl.core.menu;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karl.core.menu.api.entity.MenuEntity;
import com.karl.core.menu.api.MenuService;
import com.karl.core.menu.mapper.MenuMapper;
import org.springframework.stereotype.Service;

/**
 * 菜单 服务实现类
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Service
public class MenuImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {
}
