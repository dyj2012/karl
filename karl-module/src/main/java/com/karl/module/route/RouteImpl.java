package com.karl.module.route;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karl.module.route.api.RouteService;
import com.karl.module.route.api.entity.RouteEntity;
import com.karl.module.route.mapper.RouteMapper;
import org.springframework.stereotype.Service;

/**
 * Route 服务实现类
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Service
public class RouteImpl extends ServiceImpl<RouteMapper, RouteEntity> implements RouteService {
}
