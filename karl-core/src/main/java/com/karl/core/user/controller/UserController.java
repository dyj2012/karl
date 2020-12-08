package com.karl.core.user.controller;

import com.karl.base.annotation.LogModule;
import com.karl.base.controller.BaseEntityController;
import com.karl.core.entity.UserEntity;
import com.karl.core.user.UserServer;
import com.karl.core.user.constants.UserConstants;
import com.karl.core.user.mapper.UserMapper;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author karl
 */
@RestController
@RequestMapping("/users")
@Api(tags = "用户接口")
@LogModule("用户")
public class UserController extends BaseEntityController<UserMapper, UserEntity> implements UserServer {

    @Override
    protected void modifyEntity(UserEntity entity) {
        super.modifyEntity(entity);
        if (StringUtils.isNotBlank(entity.getPassword())) {
            entity.setPassword(UserConstants.PASSWORD.encode(entity.getPassword()));
        }
    }
}