package com.karl.module.people.controller;

import com.karl.base.controller.BaseViewController;
import com.karl.module.people.mapper.PeopleViewMapper;
import com.karl.module.view.PeopleView;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo控制器
 *
 * @author karl
 * @date 2020/5/25
 */
@RestController
@RequestMapping("/view/people")
@Api(tags = "人员视图接口")
public class PeopleViewController extends BaseViewController<PeopleViewMapper, PeopleView> {


}
