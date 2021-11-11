package com.karl.module.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.actable.annotation.Column;
import com.karl.base.actable.annotation.IsNotNull;
import com.karl.base.actable.constants.MySqlTypeConstant;
import com.karl.base.annotation.ExcelSheet;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * 教室例表
 *
 * @author karl
 * @date 2020/5/25
 */
@Data
@TableName("T_SC_CLASSROOM")
@ExcelSheet("教室")
public class ClassroomEntity extends BaseEntity {

    @TableField("NAME")
    @Column(comment = "名称", type = MySqlTypeConstant.VARCHAR, length = 64)
    @IsNotNull
    private String name;

    @TableField("CODE")
    @Column(comment = "编码", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String code;
}
