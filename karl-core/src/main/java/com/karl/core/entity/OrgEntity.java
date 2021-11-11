package com.karl.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.actable.annotation.Column;
import com.karl.base.actable.annotation.IsNotNull;
import com.karl.base.actable.constants.MySqlTypeConstant;
import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * <description>
 *
 * @author karl
 * @date 2020/5/25
 */
@Data
@TableName("T_SYS_ORG")
public class OrgEntity extends BaseEntity {

    @TableField("NAME")
    @Column(comment = "名称", type = MySqlTypeConstant.VARCHAR, length = 64)
    @IsNotNull
    private String name;

    @TableField("CODE")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    private String code;
}
