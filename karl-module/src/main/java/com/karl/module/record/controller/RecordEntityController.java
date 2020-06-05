package com.karl.module.record.controller;

import com.karl.base.controller.BaseEntityController;
import com.karl.module.entity.RecordEntity;
import com.karl.module.record.mapper.RecordMapper;
import io.swagger.annotations.Api;
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
@Api(tags = "记录接口")
public class RecordEntityController extends BaseEntityController<RecordMapper, RecordEntity> {

}
