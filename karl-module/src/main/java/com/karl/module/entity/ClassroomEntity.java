package com.karl.module.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.karl.base.annotation.ExcelName;
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
@Table(name = "T_SC_CLASSROOM")
@ExcelName("教室")
public class ClassroomEntity extends BaseEntity {

    @TableField("NAME")
    @Column(name = "NAME", comment = "名称", type = MySqlTypeConstant.VARCHAR, length = 64)
    @IsNotNull
    private String name;

    @TableField("CODE")
    @Column(name = "CODE", comment = "编码", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String code;
}
