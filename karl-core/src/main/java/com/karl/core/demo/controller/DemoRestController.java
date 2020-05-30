package com.karl.core.demo.controller;

import com.karl.base.controller.BaseRestController;
import com.karl.core.demo.api.DemoService;
import com.karl.core.demo.api.entity.DemoEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo控制器
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@RestController("demos")
public class DemoRestController extends BaseRestController<DemoEntity, DemoService> {


}
