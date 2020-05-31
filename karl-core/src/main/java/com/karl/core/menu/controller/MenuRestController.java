package com.karl.core.menu.controller;

import com.karl.base.controller.BaseRestController;
import com.karl.core.entity.MenuEntity;
import com.karl.core.menu.api.MenuService;
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
public class MenuRestController extends BaseRestController<MenuEntity, MenuService> {


}
