package com.karl.core.role.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.karl.base.controller.RestBaseController;
import com.karl.core.role.api.entity.RoleEntity;

/**
 * 角色控制器
 *
 * @author 杜永军
 * @date 2020/5/25
 */
public class RoleController extends RestBaseController<RoleEntity> {
    @Override
    protected IService<RoleEntity> getService() {
        return null;
    }

}
