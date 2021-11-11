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
@TableName("T_SYS_ROLE_MENU_R")
public class RoleMenuEntity extends BaseEntity {

    @TableField("ROLE_ID")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    @NotBlank(message = "角色不能为空", groups = AddGroup.class)
    @IsNotNull
    private String roleId;

    @TableField("MENU_ID")
    @Column(type = MySqlTypeConstant.VARCHAR, length = 64)
    @NotBlank(message = "菜单不能为空", groups = AddGroup.class)
    @IsNotNull
    private String menuId;
}
