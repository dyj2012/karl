package com.karl.core.demo;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karl.core.demo.api.DemoService;
import com.karl.core.demo.api.entity.DemoEntity;
import com.karl.core.demo.mapper.DemoMapper;

/**
 * demo 服务实现类
 *
 * @author 杜永军
 * @date 2020/5/25
 */
public class DemoImpl extends ServiceImpl<DemoMapper, DemoEntity> implements DemoService {
}
