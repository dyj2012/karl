package com.karl.core.menu.controller;

import com.karl.base.controller.BaseRestController;
import com.karl.core.menu.api.MenuService;
import com.karl.core.menu.api.entity.MenuEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * menu控制器
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@RestController("menus")
public class MenuRestController extends BaseRestController<MenuEntity, MenuService> {


}
