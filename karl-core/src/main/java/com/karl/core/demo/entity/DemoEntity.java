package com.karl.core.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.actable.annotation.Column;
import com.karl.base.actable.constants.MySqlTypeConstant;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * demo 表
 *
 * @author karl
 * @date 2020/5/25
 */
@Data
@TableName("T_MODEL_DEMO")
public class DemoEntity extends BaseEntity {

    @TableField("NAME")
    @Column(comment = "名称", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String name;

    @TableField("CODE")
    @Column(comment = "编码", type = MySqlTypeConstant.VARCHAR, length = 64)
    private String code;
}
