package com.karl.core.org.controller;

import com.karl.base.controller.BaseEntityController;
import com.karl.core.entity.OrgEntity;
import com.karl.core.org.mapper.OrgMapper;
import io.swagger.annotations.Api;
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
@Api(tags = "机构接口")
public class OrgEntityController extends BaseEntityController<OrgMapper, OrgEntity> {


}
