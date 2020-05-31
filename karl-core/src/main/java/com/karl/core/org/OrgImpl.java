package com.karl.core.org;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karl.core.entity.OrgEntity;
import com.karl.core.org.api.OrgService;
import com.karl.core.org.mapper.OrgMapper;
import org.springframework.stereotype.Service;

/**
 * 机构服务
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Service
public class OrgImpl extends ServiceImpl<OrgMapper, OrgEntity> implements OrgService {
}
