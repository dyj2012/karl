package com.karl.module.classroom.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * 教室例表
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
@TableName("T_SC_CLASSROOM")
public class ClassroomEntity extends BaseEntity {

    @TableField("NAME")
    private String name;

    @TableField("CODE")
    private String code;
}