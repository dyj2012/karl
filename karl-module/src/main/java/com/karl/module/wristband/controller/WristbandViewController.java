package com.karl.module.wristband.controller;

import com.karl.base.controller.BaseViewController;
import com.karl.module.view.WristbandView;
import com.karl.module.wristband.mapper.WristbandViewMapper;
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
@RequestMapping("/view/wristbands")
@Api(tags = "手环接口view")
public class WristbandViewController extends BaseViewController<WristbandViewMapper, WristbandView> {


}
