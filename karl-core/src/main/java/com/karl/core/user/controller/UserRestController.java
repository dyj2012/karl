package com.karl.core.user.controller;

import com.karl.base.controller.BaseRestExcelController;
import com.karl.core.entity.UserEntity;
import com.karl.core.user.api.UserService;
import com.karl.core.util.PasswordUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Think
 */
@RestController
@RequestMapping("/users")
public class UserRestController extends BaseRestExcelController<UserEntity, UserService> {

    @Override
    protected void modifyEntity(UserEntity entity) {
        super.modifyEntity(entity);
        if (StringUtils.isNotBlank(entity.getPassword())) {
            entity.setPassword(PasswordUtils.encryption(entity.getPassword()));
        }
    }
}