package com.karl.core.demo.controller;

import com.karl.base.controller.BaseExcelController;
import com.karl.core.demo.api.entity.DemoEntity;
import com.karl.core.demo.mapper.DemoMapper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo 控制器
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@RestController
@RequestMapping("/demos")
@Api(tags = "demo")
public class DemoEntityController extends BaseExcelController<DemoMapper, DemoEntity> {


}
