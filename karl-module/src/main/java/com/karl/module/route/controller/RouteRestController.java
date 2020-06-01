package com.karl.module.route.controller;

import com.karl.base.controller.BaseRestExcelController;
import com.karl.module.entity.RouteEntity;
import com.karl.module.route.api.RouteService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Route控制器
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@RestController
@RequestMapping("/routes")
@Api(tags = "路由接口")
public class RouteRestController extends BaseRestExcelController<RouteEntity, RouteService> {


}
