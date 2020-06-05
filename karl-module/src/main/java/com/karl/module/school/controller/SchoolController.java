package com.karl.module.school.controller;

import com.karl.base.controller.BaseExcelController;
import com.karl.module.entity.SchoolEntity;
import com.karl.module.school.mapper.SchoolMapper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学校控制器
 */
@RestController
@RequestMapping("/schools")
@Api(tags = "学校接口")
public class SchoolController extends BaseExcelController<SchoolMapper, SchoolEntity> {


}
