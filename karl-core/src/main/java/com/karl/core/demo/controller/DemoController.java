package com.karl.core.demo.controller;

import com.karl.base.controller.BaseEntityController;
import com.karl.core.demo.DemoServer;
import com.karl.core.demo.entity.DemoEntity;
import com.karl.core.demo.mapper.DemoMapper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo 控制器
 *
 * @author karl
 * @date 2020/5/25
 */
@RestController
@RequestMapping("/demos")
@Api(tags = "demo接口")
public class DemoController extends BaseEntityController<DemoMapper, DemoEntity> implements DemoServer {


}
