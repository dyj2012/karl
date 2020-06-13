package com.karl.module.classroom.controller;

import com.karl.base.controller.BaseEntityController;
import com.karl.module.classroom.mapper.ClassroomMapper;
import com.karl.module.entity.ClassroomEntity;
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
@RequestMapping("/classrooms")
@Api(tags = "教室接口")
public class ClassroomEntityController extends BaseEntityController<ClassroomMapper, ClassroomEntity> {


}
