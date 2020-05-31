package com.karl.module.classroom;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.karl.module.classroom.api.ClassroomService;
import com.karl.module.entity.ClassroomEntity;
import com.karl.module.classroom.mapper.ClassroomMapper;
import org.springframework.stereotype.Service;

/**
 * demo 服务实现类
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Service
public class ClassroomImpl extends ServiceImpl<ClassroomMapper, ClassroomEntity> implements ClassroomService {
}
