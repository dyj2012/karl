package com.karl.module.school.controller;

import com.karl.base.controller.BaseRestExcelController;
import com.karl.module.school.api.SchoolService;
import com.karl.module.entity.SchoolEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学校控制器
 */
@RestController
@RequestMapping("/schools")
public class SchoolRestController extends BaseRestExcelController<SchoolEntity, SchoolService> {


}
