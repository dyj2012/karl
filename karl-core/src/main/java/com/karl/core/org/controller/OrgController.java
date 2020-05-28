package com.karl.core.org.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.karl.base.controller.RestBaseController;
import com.karl.core.org.api.OrgService;
import com.karl.core.org.api.entity.OrgEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2020/5/25
 */
public class OrgController extends RestBaseController<OrgEntity> {

    @Autowired
    private OrgService orgService;

    @Override
    protected IService<OrgEntity> getService() {
        return orgService;
    }

}
