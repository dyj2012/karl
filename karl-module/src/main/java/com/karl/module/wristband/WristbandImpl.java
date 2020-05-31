package com.karl.module.wristband;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karl.module.wristband.api.WristbandService;
import com.karl.module.entity.WristbandEntity;
import com.karl.module.wristband.mapper.WristbandMapper;
import org.springframework.stereotype.Service;

/**
 * 手环 服务实现类
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Service
public class WristbandImpl extends ServiceImpl<WristbandMapper, WristbandEntity> implements WristbandService {
}
