package com.karl.core.user.controller;

import com.karl.base.annotation.LogModule;
import com.karl.base.controller.BaseEntityController;
import com.karl.core.entity.UserRoleEntity;
import com.karl.core.user.UserRoleServer;
import com.karl.core.user.mapper.UserRoleMapper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author karl
 */
@RestController
@RequestMapping("/userRole")
@Api(tags = "用户角色接口")
@LogModule("用户角色")
public class UserRoleController extends BaseEntityController<UserRoleMapper, UserRoleEntity> implements UserRoleServer {

}