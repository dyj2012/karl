package com.karl.module.route.controller;

import com.karl.base.controller.BaseEntityController;
import com.karl.module.entity.RouteEntity;
import com.karl.module.route.mapper.RouteMapper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Route控制器
 *
 * @author karl
 * @date 2020/5/25
 */
@RestController
@RequestMapping("/routes")
@Api(tags = "路由接口")
public class RouteController extends BaseEntityController<RouteMapper, RouteEntity> {


}
