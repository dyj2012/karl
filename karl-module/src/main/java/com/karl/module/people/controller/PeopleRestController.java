package com.karl.module.people.controller;

import com.karl.base.controller.BaseRestExcelController;
import com.karl.module.people.api.PeopleService;
import com.karl.module.entity.PeopleEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo控制器
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@RestController
@RequestMapping("/people")
public class PeopleRestController extends BaseRestExcelController<PeopleEntity, PeopleService> {


}
