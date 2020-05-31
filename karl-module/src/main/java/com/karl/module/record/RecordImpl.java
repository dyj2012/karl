package com.karl.module.record;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karl.module.record.api.RecordService;
import com.karl.module.entity.RecordEntity;
import com.karl.module.record.mapper.RecordMapper;
import org.springframework.stereotype.Service;

/**
 * demo 服务实现类
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Service
public class RecordImpl extends ServiceImpl<RecordMapper, RecordEntity> implements RecordService {

}
