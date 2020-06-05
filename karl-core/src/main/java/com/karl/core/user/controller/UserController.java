package com.karl.core.user.controller;

import com.karl.base.controller.BaseExcelController;
import com.karl.core.entity.UserEntity;
import com.karl.core.user.mapper.UserMapper;
import com.karl.core.util.PasswordUtils;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Think
 */
@RestController
@RequestMapping("/users")
@Api(tags = "用户接口")
public class UserController extends BaseExcelController<UserMapper, UserEntity> {


    @Override
    protected void modifyEntity(UserEntity entity) {
        super.modifyEntity(entity);
        if (StringUtils.isNotBlank(entity.getPassword())) {
            entity.setPassword(PasswordUtils.encryption(entity.getPassword()));
        }
    }
}