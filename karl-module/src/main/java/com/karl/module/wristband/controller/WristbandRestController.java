package com.karl.module.wristband.controller;

import com.karl.base.controller.BaseRestExcelController;
import com.karl.module.entity.WristbandEntity;
import com.karl.module.wristband.api.WristbandService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 手环 控制器
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@RestController
@RequestMapping("/wristbands")
@Api(tags = "手环接口")
public class WristbandRestController extends BaseRestExcelController<WristbandEntity, WristbandService> {


}
