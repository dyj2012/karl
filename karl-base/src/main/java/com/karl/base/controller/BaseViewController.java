package com.karl.base.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.karl.base.model.BaseView;
import lombok.extern.slf4j.Slf4j;

/**
 * Rest 风格controller基础类
 *
 * @author 杜永军
 * @date 2020/05/24
 */
@Slf4j
public abstract class BaseViewController<Mapper extends BaseMapper<Entity>, Entity extends BaseView> extends BaseQueryController<Mapper, Entity> {


}
