package com.karl.module.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.actable.annotation.Column;
import com.karl.base.actable.annotation.IsNotNull;
import com.karl.base.actable.constants.MySqlTypeConstant;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * 学校
 *
 * @author karl
 * @date 2020/5/25
 */
@Data
@TableName("T_SC_SCHOOL")
public class SchoolEntity extends BaseEntity {

    @TableField("NAME")
    @Column(comment = "名称", type = MySqlTypeConstant.VARCHAR, length = 128)
    @IsNotNull
    private String name;

    @TableField("CODE")
    @Column(comment = "编码", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String code;

    @TableField("ADDRESS")
    @Column(comment = "地址", type = MySqlTypeConstant.VARCHAR, length = 256)
    private String address;
}
