package com.karl.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.karl.base.actable.annotation.Column;
import com.karl.base.actable.annotation.IsNotNull;
import com.karl.base.actable.constants.MySqlTypeConstant;
import com.karl.base.model.BaseEntity;
import com.karl.base.validator.group.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 角色
 *
 * @author karl
 * @date 2020/5/25
 */
@Data
@TableName("T_SYS_ROLE")
public class RoleEntity extends BaseEntity {

    @TableField("NAME")
    @Column(comment = "名称", type = MySqlTypeConstant.VARCHAR, length = 64)
    @IsNotNull
    @NotBlank(message = "名字不能为空", groups = AddGroup.class)
    private String name;

    @TableField("CODE")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    @IsNotNull
    @NotBlank(message = "编码不能为空", groups = AddGroup.class)
    private String code;

    @TableField("REMARK")
    @Column(type = MySqlTypeConstant.VARCHAR)
    private String remark;
}
