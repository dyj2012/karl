package com.karl.module.people.controller;

import com.karl.base.controller.BaseEntityController;
import com.karl.module.entity.PeopleEntity;
import com.karl.module.people.mapper.PeopleMapper;
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
@RequestMapping("/people")
@Api(tags = "人员接口")
public class PeopleController extends BaseEntityController<PeopleMapper, PeopleEntity> {


}
