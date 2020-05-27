package com.karl.module.demo.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karl.base.controller.RestBaseController;
import com.karl.module.demo.api.entity.DemoEntity;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2020/5/25
 */
public class DemoController extends RestBaseController<DemoEntity> {
    @Override
    protected ServiceImpl<? extends BaseMapper<DemoEntity>, DemoEntity> getService() {
        return null;
    }

}
