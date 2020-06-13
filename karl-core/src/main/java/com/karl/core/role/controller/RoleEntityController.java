package com.karl.core.role.controller;

import com.karl.base.annotation.LogModule;
import com.karl.base.controller.BaseEntityController;
import com.karl.core.entity.RoleEntity;
import com.karl.core.role.mapper.RoleMapper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色控制器
 *
 * @author karl
 * @date 2020/5/25
 */
@RestController
@RequestMapping("/roles")
@Api(tags = "角色接口")
@LogModule("角色")
public class RoleEntityController extends BaseEntityController<RoleMapper, RoleEntity> {


}
