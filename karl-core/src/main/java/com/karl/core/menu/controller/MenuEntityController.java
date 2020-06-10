package com.karl.core.menu.controller;

import com.karl.base.annotation.LogModule;
import com.karl.base.controller.BaseEntityController;
import com.karl.core.entity.MenuEntity;
import com.karl.core.menu.mapper.MenuMapper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * menu控制器
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@RestController
@RequestMapping("/menus")
@Api(tags = "菜单接口")
@LogModule("菜单")
public class MenuEntityController extends BaseEntityController<MenuMapper, MenuEntity> {


}
