package com.karl.core.role.controller;

import com.karl.base.controller.BaseRestController;
import com.karl.core.role.api.RoleService;
import com.karl.core.entity.RoleEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色控制器
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@RestController
@RequestMapping("/roles")
public class RoleRestController extends BaseRestController<RoleEntity, RoleService> {


}
