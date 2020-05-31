package com.karl.module.record.controller;

import com.karl.base.controller.BaseRestController;
import com.karl.module.record.api.RecordService;
import com.karl.module.entity.RecordEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo控制器
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@RestController
@RequestMapping("/records")
public class RecordRestController extends BaseRestController<RecordEntity, RecordService> {

}
