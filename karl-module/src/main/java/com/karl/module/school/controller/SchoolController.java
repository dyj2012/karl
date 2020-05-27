package com.karl.module.school.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karl.base.controller.RestBaseController;
import com.karl.module.school.api.entity.SchoolEntity;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2020/5/25
 */
public class SchoolController extends RestBaseController<SchoolEntity> {
    @Override
    protected ServiceImpl<? extends BaseMapper<SchoolEntity>, SchoolEntity> getService() {
        return null;
    }

}
