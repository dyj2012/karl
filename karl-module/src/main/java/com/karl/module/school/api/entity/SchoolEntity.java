package com.karl.module.school.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * 学校
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
@TableName("T_SC_SCHOOL")
public class SchoolEntity extends BaseEntity {

    @TableField("NAME")
    private String name;

    @TableField("CODE")
    private String code;

    @TableField("ADDR")
    private String addr;
}
