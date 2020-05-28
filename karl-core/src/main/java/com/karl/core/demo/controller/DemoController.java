package com.karl.core.demo.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karl.base.controller.RestBaseController;
import com.karl.core.demo.api.entity.DemoEntity;

/**
 * demo控制器
 *
 * @author 杜永军
 * @date 2020/5/25
 */
public class DemoController extends RestBaseController<DemoEntity> {
    @Override
    protected IService<DemoEntity> getService() {
        return null;
    }

}
