package com.karl.module.school;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karl.module.entity.SchoolEntity;
import com.karl.module.school.api.SchoolService;
import com.karl.module.school.mapper.SchoolMapper;
import org.springframework.stereotype.Service;

/**
 * 学校
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Service
public class SchoolImpl extends ServiceImpl<SchoolMapper, SchoolEntity> implements SchoolService {
}
