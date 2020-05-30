package com.karl.module.school;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karl.module.school.api.SchoolService;
import com.karl.module.school.api.entity.SchoolEntity;
import com.karl.module.school.mapper.SchoolMapper;

/**
 * 学校
 *
 * @author 杜永军
 * @date 2020/5/25
 */
public class SchoolImpl extends ServiceImpl<SchoolMapper, SchoolEntity> implements SchoolService {
}
