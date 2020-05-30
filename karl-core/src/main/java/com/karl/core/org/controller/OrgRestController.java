package com.karl.core.org.controller;

import com.karl.base.controller.BaseRestController;
import com.karl.core.org.api.OrgService;
import com.karl.core.org.api.entity.OrgEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机构控制器
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@RestController
@RequestMapping("/orgs")
public class OrgRestController extends BaseRestController<OrgEntity, OrgService> {


}
